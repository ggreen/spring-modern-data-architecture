
function decorateProductSearchResults(productReviewSummaries)
{
    if (!productReviewSummaries || productReviewSummaries.length === 0) {
      console.log("productReviewSummaries is null, undefined, or empty");
      return "<h2>Search Results</h2><div>No products found - check if the product(s) has been loaded from source</div>";
    }


    var tableHTML = "<h2>Search Results</h2><table class='dataRows'><tr><th>Id</th><th>Name</th><th>Review</th></tr>";
    for (let x in productReviewSummaries) {
        var productReviewSummary = productReviewSummaries[x];

        tableHTML +="<tr id='productReview'>"+"<td>"+productReviewSummary.id+"</td>"+"<td>"+productReviewSummary.product.name+"</td>"+"<td>"+decorateProductReviews(productReviewSummary.productReview)+"</td>"+"</tr>";
     }
     tableHTML +="</table>";

    return tableHTML;
}

/**
 * Adding a promotion
 * @param promotion  the addd
 */
function addPromotion(promotion)
    {
       var promotionsPanel = document.getElementById("promotionsPanel");

       if(promotion == null || promotion.products.length == 0)
        {
            promotionsPanel.style = "display: none";
            return;
        }
        else
        {
            promotionsPanel.style = "display: block";
        }

        var promotionContent = document.getElementById("promotions");
        var promotionHTML = "<p>"+promotion.marketingMessage+":</p>";
         var product = {};

         promotionHTML += "<table id='dataRows'>";
         promotionHTML += "<tr><th>Product</th></tr>";

         for (let x in promotion.products) {
            product = promotion.products[x];

            promotionHTML +="<tr><td>"+product.name+"</td></tr>";
         }

         promotionHTML += "</table>";

         promotionContent.innerHTML = promotionHTML;

}//-------------------

/**
 * @productReview the product review details
 */
function decorateProductReviews(productReview)
{
    if(productReview == null)
       return "";

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
