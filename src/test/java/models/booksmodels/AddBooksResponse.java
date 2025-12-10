package models.booksmodels;

import lombok.Data;

@Data
public class AddBooksResponse {
    IsbnModel[] books;
}