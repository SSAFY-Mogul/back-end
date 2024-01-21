package com.mogul.demo.webtoon.dto;

import com.mogul.demo.common.util.ResUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonGenrePageRes extends ResUtil {
    private List data;
}
