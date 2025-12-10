package models.booksmodels;

import lombok.Data;

@Data
public class AddBooksRequestModel {
    String userId;
    IsbnModel[] collectionOfIsbns;
}