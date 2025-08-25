package com.watches.backend.service;

import com.watches.backend.Repositories.CustomerRepository;
import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.Repositories.ProductRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.*;
import com.watches.backend.model.productFeatures.*;
import com.watches.backend.service.productFeatures.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final DynamicQueryRepository<Object> dynamicQueryRepository;
    private final ColorService colorService;


    public List<Product> getRecommendations(String username) {

        List<Map<String, Integer>> indices = getFeaturesIndices();
        Map<String, Double> mergedWeight;

        List<Order> orders = orderService.getAllOrders().join();
        Map<String, Double> globalPop = new HashMap<>();
        if(!Utils.isNullOrEmpty(orders)){
            globalPop = calculateGlobal(orders);
        }

        Optional<Customer> customerOpt = customerRepository.findByEmail(username);
        Customer customer;
        Set<Long> visited = new HashSet<>();
        Wishlist wishlist = null;
        Map<String, Double> personalPop = new HashMap<>();
        if(customerOpt.isPresent()) {
            customer = customerOpt.get();
            orders = customer.getOrders();
            wishlist = customer.getWishlist();
            personalPop = calculatePersonal(orders, wishlist);
        }

        mergedWeight = mergeWeight(globalPop, personalPop);
        List<Double[]> pv = new ArrayList<>(getOrderVectors(orders, indices, mergedWeight));

        pv.addAll(getOrderVectors(orders, indices, mergedWeight));
        if(wishlist != null) {
            for (Product product : wishlist.getProducts()) {
                visited.add(product.getId());
                pv.add(getVector(product, indices, mergedWeight));
            }
        }

        if(Utils.isNullOrEmpty(pv)){
            return generateRandom();
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
                    Double cos1 = cosineSimilarity(baseVector, getVector(p1, indices, mergedWeight));
                    Double cos2 = cosineSimilarity(baseVector, getVector(p2, indices, mergedWeight));
                    return cos2.compareTo(cos1);
                })
                .limit(5)
                .toList();
    }

    private List<Product> generateRandom(){
        return productRepository.findAll().stream().limit(5).toList();
    }

    private List<Double[]> getOrderVectors(List<Order> orders,List<Map<String, Integer>> indices, Map<String, Double> popularity){
        List<Double[]> pv = new ArrayList<>();
        if(Utils.isNullOrEmpty(orders)) return pv;
        for(Order order : orders){
            for(OrderItem item : order.getItems()){
                Product product = item.getProduct();
                pv.add(getVector(product, indices, popularity));
            }
        }
        return pv;
    }

    private Map<String, Double> mergeWeight(Map<String, Double> global, Map<String, Double> personal){
        if(Utils.isNullOrEmpty(global)) return personal;
        if(Utils.isNullOrEmpty(personal)) return global;
        Map<String, Double> merged = new HashMap<>();
        for(String key : global.keySet()){
            merged.put(key, global.get(key) + personal.getOrDefault(key, 0.0));
        }

        for(String key : personal.keySet()){
            if(!merged.containsKey(key)){
                merged.put(key, global.getOrDefault(key, 0.0) + personal.get(key));
            }
        }
        return merged;
    }

    private Map<String, Double> calculateGlobal(List<Order> orders){
        Map<String, Double> map = calculateOrdersWeight(orders);
        long total = (long) map.values().stream().mapToDouble(Double::doubleValue).sum();
        map.replaceAll((k, v) -> map.get(k) / total);
        return map;
    }

    private Map<String, Double> calculatePersonal(List<Order> orders, Wishlist wishlist){
        Map<String, Double> map = calculateOrdersWeight(orders);
        for(Product product : wishlist.getProducts()){
            map.put(product.getBrand().getName(), map.getOrDefault(product.getBrand().getName(), 0.0) + 1);
            map.put(product.getBand().getMaterial(), map.getOrDefault(product.getBand().getMaterial(), 0.0) + 1);
            map.put(product.getACase().getMaterial(), map.getOrDefault(product.getACase().getMaterial(), 0.0) + 1);
            map.put(product.getNumberingFormat().getFormat(), map.getOrDefault(product.getNumberingFormat().getFormat(), 0.0) + 1);
            map.put(product.getDisplayType().getType(), map.getOrDefault(product.getDisplayType().getType(), 0.0) + 1);
            map.put(product.getShape().getName(), map.getOrDefault(product.getShape().getName(), 0.0) + 1);
        }
        long total = (long) map.values().stream().mapToDouble(Double::doubleValue).sum();
        map.replaceAll((k, v) -> map.get(k) / total);
        return map;
    }

    private Map<String, Double> calculateOrdersWeight(List<Order> orders){
        Map<String, Double> map = new HashMap<>();
        for(Order order : orders){
            for(OrderItem item : order.getItems()){
                Product product = item.getProduct();
                map.put(product.getBrand().getName(), map.getOrDefault(product.getBrand().getName(), 0.0) + item.getQuantity());
                map.put(product.getBand().getMaterial(), map.getOrDefault(product.getBand().getMaterial(), 0.0) + item.getQuantity());
                map.put(product.getACase().getMaterial(), map.getOrDefault(product.getACase().getMaterial(), 0.0) + item.getQuantity());
                map.put(product.getNumberingFormat().getFormat(), map.getOrDefault(product.getNumberingFormat().getFormat(), 0.0) + item.getQuantity());
                map.put(product.getDisplayType().getType(), map.getOrDefault(product.getDisplayType().getType(), 0.0) + item.getQuantity());
                map.put(product.getShape().getName(), map.getOrDefault(product.getShape().getName(), 0.0) + item.getQuantity());
            }
        }
        return map;
    }

    private Double cosineSimilarity(Double[] vec1, Double[] vec2) {
        if(vec1 == null || vec2 == null || vec1.length != vec2.length){
            return 0.0;
        }
        double dot = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for(int i = 0;i < vec1.length;i++) {
            dot   += vec1[i] * vec2[i];
            norm1 += vec1[i] * vec1[i];
            norm2 += vec2[i] * vec2[i];
        }
        if(norm1 == 0.0 || norm2 == 0.0) return 0.0;
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private <T> Map<String, Integer> getFeatureIndices(Class<T> clazz, String column){
        Set<String> fValues = dynamicQueryRepository.getDistinctValues(clazz, column);
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

    private Double[] getVector(Product product, List<Map<String, Integer>> indices, Map<String, Double> weight){
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
                vector[offset + ind.get(value)] = weight.getOrDefault(value, 0.0);
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
