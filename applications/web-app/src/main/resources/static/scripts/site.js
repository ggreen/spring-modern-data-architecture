
function decorateProductReviews(productReview)
{
    var customerReview  = {};
    //<tr><th>sentiment</th><th>Comment</th></tr>
    var productReviewsHtml = "<table>";
    for (let x in productReview.customerReviews) {
            customerReview = productReview.customerReviews[x];

            productReviewsHtml +="<tr><td>"+decorateSentiment(customerReview.sentiment)+"</td><td>"+customerReview.id+"</td><td>"+customerReview.review+"</td></tr>";
         }
    return productReviewsHtml+"</table>";
}

function decorateSentiment(sentiment)
{
    var sentimentHtml = "";

    //w3-block
    if( "POSITIVE" == sentiment )
        sentimentHtml = "<button class='positive-box' title='Accept'>positive</button>";
    else
        sentimentHtml = "<button class='negative-box' title='Decline'>negative</button>";

    return     sentimentHtml;
}
