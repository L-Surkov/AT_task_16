package models.booksmodels;

import lombok.Data;

@Data
public class DeleteBookRequestBody {
    String userId,
            isbn;
}