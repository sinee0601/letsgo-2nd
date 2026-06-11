package com.travel.letsgospringboot.tourapi.repository;

import com.travel.letsgospringboot.tourapi.vo.TourPlaceVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TourImportMapper {

    int insertPlace(TourPlaceVO place);
}
