package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    public String id;
    public String title;
    public String author;
    public String cover;
    public List<ReviewDto> reviews;

    public static class ReviewDto {

        public String userFullName;
        public Short rating;
        public List<Recommendation> recommendation;
        public String comment;
    }
}
