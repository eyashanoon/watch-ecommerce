package com.watches.backend.service;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.model.*;
import com.watches.backend.model.productFeatures.*;
import com.watches.backend.service.productFeatures.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final DynamicQueryRepository<Object> dynamicQueryRepository;
    private final ColorService colorService;


    @SuppressWarnings("DuplicatedCode")
    public List<Product> getAuthorizedRecommendations(String username) {
        Customer customer = customerService.getCustomerByUsername(username).join();
        List<Order> orders = customer.getOrders();
        List<Map<String, Integer>> indices = getFeaturesIndices();
        List<Double[]> pv = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        for(Order order : orders){
            for(OrderItem item : order.getItems()){
                Product product = item.getProduct();
                visited.add(product.getId());
                pv.add(getVector(product, indices));
            }
        }
        Wishlist wishlist = customer.getWishlist();
        for(Product product : wishlist.getProducts()){
            visited.add(product.getId());
            pv.add(getVector(product, indices));
        }
        if(pv.isEmpty()){
            return getRecommendations();
        }
        Double[] baseVector = combine(pv);

        return productRepository.findAll().stream()
                .sorted((p1, p2) -> {
                    if(visited.contains(p1.getId()) || p1.getDeleted()){
                        return 1;
                    }
                    if(visited.contains(p2.getId()) || p2.getDeleted()){
                        return -1;
                    }
                    Double cos1 = cosineSimilarity(baseVector, getVector(p1, indices));
                    Double cos2 = cosineSimilarity(baseVector, getVector(p2, indices));
                    return cos2.compareTo(cos1);
                })
                .toList();
    }

    @SuppressWarnings("DuplicatedCode")
    public List<Product> getRecommendations(){
        List<Order> orders = orderService.getAllOrders().join();
        List<Map<String, Integer>> indices = getFeaturesIndices();
        List<Double[]> pv = new ArrayList<>();
        for(Order order : orders){
            for(OrderItem item : order.getItems()){
                Product product = item.getProduct();
                pv.add(getVector(product, indices));
            }
        }
        Double[] baseVector = combine(pv);

        return productRepository.findAll().stream()
                .sorted((p1, p2) -> {
                    if(p1.getDeleted()){
                        return 1;
                    }
                    if(p2.getDeleted()){
                        return -1;
                    }
                    Double cos1 = cosineSimilarity(baseVector, getVector(p1, indices));
                    Double cos2 = cosineSimilarity(baseVector, getVector(p2, indices));
                    return cos2.compareTo(cos1);
                })
                .toList();
    }

    private Double cosineSimilarity(Double[] vec1, Double[] vec2) {
        double dot = 0.0;
        double norm1 = 0.0, norm2 = 0.0;
        int size = vec1.length;
        for(int i = 0;i < size;i++) {
            dot += vec1[i] * vec2[i];
            norm1 += vec1[i] * vec1[i];
            norm2 += vec2[i] * vec2[i];
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private <T> Map<String, Integer> getFeatureIndices(Class<T> clazz, String column){
        Set<String> fValues = dynamicQueryRepository.getDistinctValues((Class<Object>) clazz, column);
        Map<String, Integer> indices = new HashMap<>();
        for(String value : fValues){
            indices.put(value, indices.size());
        }
        return indices;
    }

    private Map<String, Integer> getPartIndices(Set<String> colors){
        Map<String, Integer> indices = new HashMap<>();
        for(String color : colors){
            indices.put(color, indices.size());
        }
        return indices;
    }

    private List<Map<String, Integer>> getColorFeatureIndices(){
        Map<String, Set<String>> colors = colorService.getAllColors().join();
        List<Map<String, Integer>> indices = new ArrayList<>();
        indices.add(getPartIndices(colors.get("Hands")));
        indices.add(getPartIndices(colors.get("Background")));
        indices.add(getPartIndices(colors.get("Band")));
        return indices;
    }

    private List<Map<String, Integer>> getFeaturesIndices() {
        List<Map<String, Integer>> indices = new ArrayList<>();
        indices.add(getFeatureIndices(Brand.class, "name"));
        indices.add(getFeatureIndices(Band.class, "material"));
        indices.add(getFeatureIndices(Case.class, "material"));
        indices.add(getFeatureIndices(DisplayType.class, "type"));
        indices.add(getFeatureIndices(NumberingFormat.class, "format"));
        indices.add(getFeatureIndices(Shape.class, "name"));
        indices.addAll(getColorFeatureIndices());
        return indices;
    }

    private Double[] getVector(Product product, List<Map<String, Integer>> indices){
         int vectorSize = indices.stream().mapToInt(Map::size).sum();
         Double[] vector = new Double[vectorSize + 9];
         Arrays.fill(vector, 0.0);
         int offset = 0;
         for(int idx = 0;idx < indices.size();idx++){
            Map<String, Integer> ind = indices.get(idx);
            String value = switch (idx){
                case 0 -> product.getBrand().getName();
                case 1 -> product.getBand().getMaterial();
                case 2 -> product.getACase().getMaterial();
                case 3 -> product.getDisplayType().getType();
                case 4 -> product.getNumberingFormat().getFormat();
                case 5 -> product.getShape().getName();
                case 6 -> product.getColors().get(0).getColor();
                case 7 -> product.getColors().get(1).getColor();
                case 8 -> product.getColors().get(2).getColor();
                default -> null;
            };
            if(value != null && ind.containsKey(value)){
                vector[offset + ind.get(value)] = 1.0;
            }
            offset += ind.size();
         }

         vector[vectorSize    ] = product.getWaterProof() ? 1.0 : 0.0;
         vector[vectorSize + 1] = product.getHasTickingSound() ? 1.0 : 0.0;
         vector[vectorSize + 2] = product.getIncludesDate() ? 1.0 : 0.0;
         vector[vectorSize + 3] = product.getHasFullNumerals() ? 1.0 : 0.0;
         vector[vectorSize + 4] = product.getChangeableBand() ? 1.0 : 0.0;

         vector[vectorSize + 5] = getNormalized(product.getPrice());
         vector[vectorSize + 6] = getNormalized(product.getSize());
         vector[vectorSize + 7] = getNormalized(product.getWeight());
         vector[vectorSize + 8] = getNormalized(product.getQuantity());

         return vector;
    }

    private <T extends Number> Double getNormalized(T price){
        int base10 = 1;
        double copy = price.doubleValue();
        while(copy >= 100){
            copy /= 10;
            base10 *= 10;
        }
        copy = price.doubleValue();
        return copy / base10;
    }

    private Double[] combine(List<Double[]> pv){
        Double[] sum = new Double[pv.get(0).length];
        Arrays.fill(sum, 0.0);

        for(Double[] vector : pv){
            for(int i = 0;i < sum.length;i++){
                sum[i] += vector[i];
            }
        }

        for(int i = 0;i < sum.length;i++){
            sum[i] /= pv.size();
        }
        return sum;
    }

}
