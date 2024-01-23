package com.mogul.demo.webtoon.response;

import com.mogul.demo.common.util.ResUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonDetailPageRes extends ResUtil {
    private Map<String, Object> data;
}
