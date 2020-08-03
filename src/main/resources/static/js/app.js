
function getUserSession(){
	return JSON.parse(sessionStorage.getItem("userSession"));
}



function checkUserSession(){
	var usersession = JSON.parse(sessionStorage.getItem("userSession"));
	if(usersession == null || usersession == undefined){
		window.location = "login.html";
	}else{
		$("#logged-user").html(getUserSession().username);
		if(usersession.roleName == "Operator"){
			$('#user-button').hide();
			$('#new-user-button').hide();
		}
	}
}

function showErrorMessage(errorMessage){
	
	$("#errorMessage").html('Error - ' + errorMessage);
    $(".alert").show();
	
}
function showSuccessMessage(successMessage){
	
	$("#successMessage").html(successMessage);
    $(".success").show();
	
}

function loadMonthValues() {
	$.ajax({
        url: 'http://localhost:8080/api/v1/creditcard/months',
        type:'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
        	if(response.success){
        		var i;
    			for (i = 0; i < response.entity.length; i++) {
            		var entity = response.entity[i];
            		$('#inputmonth').append(new Option(entity.value, entity.value));

    			}    
        	}else{
            	showErrorMessage(response.message);
        	}
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);
        }
    });
	
}

function loadYearValues() {
	var currentYear = new Date().getFullYear();
	var i;
	for (i = 0; i < 11; i++) {
		$('#inputyear').append(new Option(currentYear + i, currentYear + i));
	}
	
}

function loadRoleValues(){
	$.ajax({
        url: 'http://localhost:8080/api/v1/user/roles',
        type:'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
        	if(response.success){
        		var i;
    			for (i = 0; i < response.entity.length; i++) {
            		var entity = response.entity[i];
            		$('#inputrole').append(new Option(entity.description, entity.name));

    			}    
        	}else{
            	showErrorMessage(response.message);
        	}
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });

}

function loadUsers() {
	
	var loggedUserId = getUserSession().id;
	
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/all/'+loggedUserId,
        type:'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
        	if(response.success){
        		var i;
    			for (i = 0; i < response.entity.length; i++) {
            		var entity = response.entity[i];
    				var index = i+1;
    				$("#userTable").append('<tr> <th scope="row">'+ index +'</th> <td>'+ entity.username +'</td> <td>'+ entity.roleName +'</td> </tr>')
    			}    
        	}else{
        		
            	showErrorMessage(response.message);
        	}
        	
        	
        	
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });
}

function saveUser(data) {
	
	var loggedUserId = getUserSession().id;
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/'+loggedUserId,
        type:'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data:JSON.stringify(data),
        success:function(response){	
        	if(response.success){
        		fowardUsers();
        		showSuccessMessage(response.message);
        	}else{
            	showErrorMessage(response.message);
        	}
        	
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });
}


function creditcard(number) {
	$("#creditcardTable tbody").empty();
	document.getElementById("creditcardTable").deleteCaption();
	var loggedUserId = getUserSession().id;
    $.ajax({
        url: 'http://localhost:8080/api/v1/creditcard/all/'+loggedUserId+'/'+ number,
        type:'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
    	
        	if(response.success){
        		
        		var entity = response.entity;
        		var i;
        			for (i = 0; i < entity.length; i++) {
        				var credicard = entity[i];
        				var expiryDate = credicard.year + '/' + credicard.month;
        				var param = "'?id=" +credicard.id+"'";
        				var edit = '<button type="button" class="btn btn-primary" onclick="fowardSaveCreditcard('+ param +')">Edit </button>';
        				var index = i+1;
        				$("#creditcardTable").append('<tr> <th scope="row">'+ index +'</th> <td>'+ credicard.holderName +'</td> <td>'+ credicard.number +'</td>  <td>'+ credicard.expiryDate +'</td>  <td>'+ edit +'</td> </tr>')
        			}  
        			
        	}
        	
        	$("#creditcardTable").append('<caption>'+ response.message +'</caption>');
			    	
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });
}

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};

function creditcardById(id){
	
	var loggedUserId = getUserSession().id;
	var creditcard = {};
    $.ajax({
        url: 'http://localhost:8080/api/v1/creditcard/'+loggedUserId+'/'+ id,
        type:'GET',
        
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success:function(response){	
        	if(response.success){
        		creditcard = response.entity;
        		$('#inputcreditcardid').val(creditcard.id);
        		$('#inputholdername').val(creditcard.holderName);
        		$('#inputnumber').val(creditcard.number);
        		$('#inputyear').val(creditcard.year);
        		$('#inputmonth').val(creditcard.month);
        		$('#inputholdername').prop( "disabled", true );
        		$('#inputnumber').prop( "disabled", true );
        			
        	}			    	
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });
    
    return creditcard;
	
}

function edit(creditcard){
		$('#inputcreditcardid').val(creditcard.id);
		$('#inputholdername').val(creditcard.holderName);
		$('#inputnumber').val(creditcard.number);
		$('#inputyear').val(creditcard.year);
		$('#inputmonth').val(creditcard.month);
		
		$('#inputcreditcardid').prop( "disabled", true );
		$('#inputholdername').prop( "disabled", true );
		$('#inputnumber').prop( "disabled", true );
	

	

	
}

function fowardSaveCreditcard(parameters){
	var param = parameters == undefined ? "" : parameters;
	window.location = "creditcard-details.html" + param;
}

function fowardCreditcards(){
	window.location = "creditcards.html";
	
}

function savecrediticard(data, type){
	var loggedUserId = getUserSession().id;
	$.ajax({
        url: 'http://localhost:8080/api/v1/creditcard/'+loggedUserId,
        type:type,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data:JSON.stringify(data),
        success:function(response){	
    	
        	if(response.success){
        		fowardCreditcards();
        		showSuccessMessage(response.message);
        	}else{
            	showErrorMessage(response.message);
        	}
        },
        error: function(xhr, status, error){
        	showErrorMessage(xhr.status + ': ' + xhr.statusText);

        }
    });
}

function fowardSaveUser(){
	window.location = "user-details.html";

}
function fowardUsers(){
	window.location = "users.html";

}

$(document).ready(function() {
	
	
	 
	  $.fn.inputFilter = function(inputFilter) {
		    return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
		      if (inputFilter(this.value)) {
		        this.oldValue = this.value;
		        this.oldSelectionStart = this.selectionStart;
		        this.oldSelectionEnd = this.selectionEnd;
		      } else if (this.hasOwnProperty("oldValue")) {
		        this.value = this.oldValue;
		        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
		      } else {
		        this.value = "";
		      }
		    });
		  };
	
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
                	 sessionStorage.setItem("userSession", JSON.stringify(response.entity));
                     window.location = "creditcards.html";
                 }else{
                	 $("#message").html(response.message);
                	 $(".alert").show();
                 }
                
             },
             error: function(xhr, status, error){
             	showErrorMessage(xhr.status + ': ' + xhr.statusText);

             }
         });
	 });
	 
	 $("#logout-button").click(function( event ) {
		sessionStorage.removeItem("userSession");
		window.location = "login.html";
		 
	 });
	 
	 $("#creditcard-search-form").submit(function( event ) {
		 event.preventDefault();
		 var $form = $( this ),
         	number = $form.find( "input[name='numberInput']" ).val();
		 creditcard(number);
		 
	 });
	 
	 $("#creditcard-save-form").submit(function( event ) {
		 event.preventDefault();
		 var $form = $( this ),
		 creditcardid = $form.find( "input[name='inputcreditcardid']" ).val(),	 
		 holdername = $form.find( "input[name='inputholdername']" ).val(),
		 number = $form.find( "input[name='inputnumber']" ).val(),
		 year = $form.find( "select[name='inputyear']" ).val(),
		 month = $form.find( "select[name='inputmonth']" ).val();
		 
		 var data = { id: creditcardid , holderName : holdername, number: number, year: year, month: month };
		 var type = creditcardid != "" && creditcardid != undefined && creditcardid != null ? 'PUT' : 'POST'
		 savecrediticard(data, type);
		 
	 });
	 
	 $("#user-save-form").submit(function( event ) {
		 event.preventDefault();
		 var $form = $( this ),
		 username = $form.find( "input[name='inputusername']" ).val(),	 
		 password = $form.find( "input[name='inputpassword']" ).val(),
		 role = $form.find( "select[name='inputrole']" ).val();
	 
		 var data = { username: username , password : password, roleName: role };
		 saveUser(data);
	 });
	 
});