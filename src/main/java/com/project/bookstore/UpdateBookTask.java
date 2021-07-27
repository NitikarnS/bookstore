package com.project.bookstore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.project.bookstore.Model.Book;
import com.project.bookstore.Model.PublisherBook;
import com.project.bookstore.Repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UpdateBookTask {

    @Autowired
	BookRepository bookRepository;

    @Value("${publisher.api.books.url}")
	private String bookUrl;

	@Value("${publisher.api.books.recommendation.url}")
	private String recommendationBookUrl;

    @Scheduled(cron  =  "0 0 0 ? * SUN" )
    public void updateBooks() {
        System.out.println("========== Update Books ==========");

        System.out.println("========== Get Publishers Book  ==========");
        RestTemplate restTemplate = new RestTemplate();
		PublisherBook[] books = restTemplate.getForObject(bookUrl, PublisherBook[].class);
		List<PublisherBook> bookList = Arrays.stream(books).collect(Collectors.toList());

		PublisherBook[] recommendationBooks = restTemplate.getForObject(recommendationBookUrl, PublisherBook[].class);
		List<PublisherBook> recommendationBookList = Arrays.stream(recommendationBooks).collect(Collectors.toList());
        System.out.println("========== Get Publisher Books END ==========");

        System.out.println("========== Insert Books ==========");
		bookRepository.deleteAll();
		for (PublisherBook book : bookList) {
			boolean isRecommendation = recommendationBookList.stream().anyMatch(b -> b.getId().equals(book.getId()));
			System.out.println("insert " + bookRepository.save(
					new Book(book.getId(), book.getName(), book.getAuthorName(), book.getPrice(), isRecommendation)));
		}
        System.out.println("========== Insert Books End ==========");

        System.out.println("========== Update Books End ==========");
    }

    public boolean shouldUpdateBook() {
        return bookRepository.findAll().isEmpty();
    }

}
