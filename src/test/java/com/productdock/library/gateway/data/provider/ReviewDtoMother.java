package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.Recommendation;

import java.util.List;

public class ReviewDtoMother {

    private static final String defaultUserFullName = "John Doe";
    private static final Short defaultRating = 5;
    private static final List<Recommendation> defaultRecommendation= List.of(Recommendation.JUNIOR);
    private static final String defaultComment = "Must read!";

    public static BookDto.ReviewDto defaultReviewDto() {
        return defaultReviewDtoBuilder().build();
    }

    public static BookDto.ReviewDto.ReviewDtoBuilder defaultReviewDtoBuilder() {
        return BookDto.ReviewDto.builder();
    }
}
