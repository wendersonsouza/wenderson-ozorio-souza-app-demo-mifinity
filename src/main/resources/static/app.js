function loadUser() {
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/'
        type:'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
    	
        	if(response.success){
        		
			    	<tr> <th scope="row">1</th> <td>Mark</td> <td>Mark </td> </tr>
			    	
				        $("#attempt-form").find( "input[name='result-attempt']" ).val("");
				        $("#attempt-form").find( "input[name='user-alias']" ).val("");
				        
				        $('.multiplication-a').empty().append(data.factorA);
				        $('.multiplication-b').empty().append(data.factorB);
        	}
        }
}


$(document).ready(function() {
	
	 $("#login-form").submit(function( event ) {
		 
		 var url = 'http://localhost:8080/api/v1/auth/login'
		 event.preventDefault();
		 var $form = $( this ),
         	user = $form.find( "input[name='inputUsername']" ).val(),
         	psw  = $form.find( "input[name='inputPassword']" ).val();
		 var data = { username: user, password: psw};
		 
		 $.ajax({
             url:url,
             type:'POST',
             data:JSON.stringify(data),
             contentType: "application/json; charset=utf-8",
             dataType: "json",
             success:function(response){
                 var msg = "";
                 if(response.success){
                	 sessionStorage.setItem("userSession", response.entity);
                     window.location = "creditcards.html";
                 }else{
                     msg = "Invalid username and password!";
                 }
                 //$("#message").html(msg);
             }
         });
	 });
	 
});