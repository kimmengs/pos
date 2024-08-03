package com.test.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;


import com.test.demo.helper.Helper;
import com.test.demo.models.Item;
import com.test.demo.models.MessageDto;
import com.test.demo.models.VwItem;
import com.test.demo.repository.ItemRepository;
import com.test.demo.repository.VwItemRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VwItemRepository vwItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Cacheable(value = "vwItems", key = "#id")
    public VwItem getItemById(String id) {
        return vwItemRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "vwItems", key = "#page + '-' + #size")
    public List<VwItem> getAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VwItem> vwPage = vwItemRepository.findAllByStatusOrderByCreatedDateDesc(Helper.ACTIVE, pageable);
        return vwPage.getContent();
    }

    @CacheEvict(value = { "items", "vwItems" }, allEntries = true)
    @CachePut(value = "items", key = "#result.id")
    public Item createItem(Item item) {
        item.setId(UUID.randomUUID().toString());
        item.setCreatedDate(new Date());
        item.setStatus(Helper.ACTIVE);
        itemRepository.save(item);

        List<ServiceInstance> instances = discoveryClient.getInstances("TELEGRAM-SVC1");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("Service logs-service not available");
        }

        ServiceInstance instance = instances.get(0);
        MessageDto content = new MessageDto("494889851", "Item created with id: " + item.getFullName());
        String url = String.format("http://%s:%d/telegram/send", instance.getHost(), instance.getPort());
        restTemplate.postForObject(url, content, String.class);

        // restTemplate.postForObject("http://localhost:8082/telegram/send", content,
        //         String.class);

        return item;
    }

    @CacheEvict(value = { "items", "vwItems" }, allEntries = true)
    @CachePut(value = "items", key = "#result.id")
    public Item updateItem(Item item) {
        item.setStatus("UPDATED");
        item.setCreatedDate(new Date());
        Item resultItem = itemRepository.save(item);
        return resultItem;
    }

    @CacheEvict(value = "items", key = "#id")
    public void deleteItemById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            Item resultItem = item.get();
            resultItem.setStatus("DELETED");
            itemRepository.save(resultItem);
        } else {
            throw new RuntimeException("Item not found with id: " + id);
        }
    }

}
