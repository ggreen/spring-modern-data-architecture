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
    public int compareTo(CustomerReview other) {

        if(other == null)
            return 1;

        // Compare id first
        int cmp = compareStrings(this.id, other.id);
        if (cmp != 0) return cmp;

        // Then compare productId
        cmp = compareStrings(this.productId, other.productId);
        if (cmp != 0) return cmp;

        // Finally compare review
        return compareStrings(this.review, other.review);
    }

    private static int compareStrings(String s1, String s2) {
        if (s1 == s2) return 0;         // same object or both null
        if (s1 == null) return -1;      // nulls first
        if (s2 == null) return 1;
        return s1.compareTo(s2);        // lexicographical
    }
}
