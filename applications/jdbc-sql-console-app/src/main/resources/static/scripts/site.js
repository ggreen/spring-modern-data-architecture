const textarea = document.getElementById('sql');
const execBtn = document.getElementById('execBtn');
const clearBtn = document.getElementById('clearBtn');
const output = document.getElementById('output');

function renderMessage(msg){
  output.innerHTML = msg;
}

function runSQL(sqlText){
      if(!sqlText || !sqlText.trim()){
        renderMessage('Please enter a SQL statement.');
        return;
      }
      // Demo behavior: echo the SQL and simulate a response
      const timestamp = new Date().toLocaleString();

      $.ajax({
              url:         "sql",
              type:        "POST",
              data:        sqlText,
              contentType: "text/plain; charset=utf-8",
              dataType:    "text",
              success:     function(data, status){

                renderMessage(data);
            },
            error: function (request, status, error) {
              var htmlError = "<div style='color:red'>"+
                               request.responseText+
                               "</div>";

                   renderMessage(htmlError);
            });
    }