package models.accountmodels;

import lombok.Data;
import models.booksmodels.BookResponseModel;

@Data
public class UserModel {
    String userId,
            username;
    BookResponseModel[] books;
}