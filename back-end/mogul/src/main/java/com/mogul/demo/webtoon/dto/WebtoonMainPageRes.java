package com.mogul.demo.webtoon.dto;

import com.mogul.demo.common.util.ResUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonMainPageRes extends ResUtil {
    private Map<String, List> data;
}
