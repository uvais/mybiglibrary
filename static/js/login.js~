// Login Form
$(document).ready(function() {
	$('.mainDiv').html($('#mainDivRep').html());
    var button = $('#loginButton');
    var box = $('#loginBox');
    var form = $('#loginForm');
	button.removeAttr('href');
    button.mouseup(function(login) {
        box.toggle();
        button.toggleClass('active');
    });
	form.mouseup(function() { 
        return false;
    });
    $(this).mouseup(function(login) {
        if(!($(login.target).parent('#loginButton').length > 0)) {
            button.removeClass('active');
            box.hide();
        }
    });



$(this).keyup(function(event) {
	if (event.which == 27) { // 27 is 'Ecs' in the keyboard
		$('#resultBox').css("display", "none");  // function close pop up
	}  	
});


$("#uploadSideButton").on('click', this, function(){
	$('.mainDiv').html($('#uploadDiv').html());
});
$("#searchSideButton").on('click', this, function(){
	$('.mainDiv').html($('#mainDivRep').html());
});

});
