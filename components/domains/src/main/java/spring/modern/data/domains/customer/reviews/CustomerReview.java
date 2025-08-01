package spring.modern.data.domains.customer.reviews;

import lombok.Builder;

/**
 * Customer review data
 * @param id customer Id
 * @param review the review
 * @param sentiment the review sentiment
 */
@Builder
public record CustomerReview(String id, String productId, String review, Sentiment sentiment) implements Comparable<CustomerReview> {

    @Override
    public int compareTo(CustomerReview o) {
        if(o == null)
            return 1;

        if(this.id == o.id)
            return 0;

        if(this.id == null)
            return 1;

        return this.id.compareTo(o.id);
    }
}
