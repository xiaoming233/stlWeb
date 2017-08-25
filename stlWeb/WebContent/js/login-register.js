/*
 *
 * login-register modal
 * Autor: Creative Tim
 * Web-autor: creative.tim
 * Web script: #
 * 
 */
function showRegisterForm(){
    $('.loginBox').fadeOut('fast',function(){
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast',function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('欢迎注册');
    }); 
    $('.error').removeClass('alert alert-danger').html('');
       
}
function showLoginForm(){
    $('#loginModal .registerBox').fadeOut('fast',function(){
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast',function(){
            $('.login-footer').fadeIn('fast');    
        });
        
        $('.modal-title').html('欢迎登录');
    });       
     $('.error').removeClass('alert alert-danger').html(''); 
}

function openLoginModal(){
    showLoginForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}
function openRegisterModal(){
    showRegisterForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}

function loginAjax(){

	if($('#email').val=='')
	{
		shakeModal('请输入账户邮箱！'); 	
	}
    $.post( "/login", 
    		{username:$('#username').val(),
    	password:$('#password').val()}, 
    	function( data ) {
    	  var strJson =strToJson(data);
            if(strJson.result == 2){
                         
            } 
            else if(strJson.result == 1) {
                 shakeModal('密码不正确！'); 
            }
            else  if(strJson.result == 0){
            	shakeModal('用户不存在！');
			}
            else {
            	shakeModal('未知错误，请联系系统管理员！');
			}
        });

/*   Simulate error message from the server   */
}

function shakeModal(massage){
    $('#loginModal .modal-dialog').addClass('shake');
             $('.error').addClass('alert alert-danger').html(massage);
             $('input[type="password"]').val('');
             setTimeout( function(){ 
                $('#loginModal .modal-dialog').removeClass('shake'); 
    }, 1000 ); 
}

   