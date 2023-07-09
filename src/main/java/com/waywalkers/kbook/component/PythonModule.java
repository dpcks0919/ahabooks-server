package com.waywalkers.kbook.component;

import com.google.gson.Gson;
import com.waywalkers.kbook.domain.evaluation.Evaluation;
import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Component
public class PythonModule {

    private final RecordRepository recordRepository;
    private final RestTemplate restTemplate;

    public List<BookDto.BookContent> makeBookContents(long id) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("host.docker.internal:5000")
                .path("/get")
                .queryParam("id", id).build();

        ResponseEntity response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        JSONParser jsonParser = new JSONParser();
        Gson gson = new Gson();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");

        List<BookDto.BookContent> bookContents = (List<BookDto.BookContent>) jsonArray.parallelStream().map(bookContentObject -> gson.fromJson(((JSONObject) bookContentObject).toJSONString(), BookDto.BookContent.class)).collect(Collectors.toList());
        return bookContents;
    }

    public Evaluation makeEvaluation(long id) throws Exception{
        Record record = recordRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("record"));
        String recordUrl = record.getRecordFileUrl();
        String hwpUrl = record.getProfileBookRelation().getBook().getBookFileUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("hwpUrl", hwpUrl);
        map.put("recordUrl", recordUrl);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("host.docker.internal:5000")
                .path("/evaluation")
                .queryParam("recordId", id)
                .build();

        ResponseEntity response = restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);
        log.info((String) response.getBody());

        JSONParser jsonParser = new JSONParser();
        Gson gson = new Gson();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
        Evaluation evaluation = gson.fromJson( ((JSONObject)jsonObject.get("data")).toJSONString() , Evaluation.class);
        return evaluation;
    }

}
