package com.mogul.demo.webtoon.dto;

import com.mogul.demo.common.util.ResUtil;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonAllPageRes extends ResUtil {
    private List data;
}
