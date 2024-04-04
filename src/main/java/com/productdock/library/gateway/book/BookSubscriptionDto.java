package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSubscriptionDto {
    private String bookId;
    private String userId;
    private Date createdDate;
}
