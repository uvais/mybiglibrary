// Login Form
$(document).ready(function() {
$('.mainDiv').html($('#hadoopDiv').html());

$("#btnHadoop").on('click', this, function(){
	$('.mainDiv').html($('#hadoopDiv').html());
});
$("#btnAddUser").on('click', this, function(){
	$('.mainDiv').html($('#userAddDiv').html());
});

});
