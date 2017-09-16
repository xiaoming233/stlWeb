
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
	var _username=$('#username').val();
	if(_username=='')
	{
		shakeModal('请输入用户名！'); 	
	}
	else if ($('#password').val()=='')
	{
		shakeModal('请输入密码！'); 	
	}
	else{
		$.post( "login", 
	    		{username:_username,
	    	password:$('#password').val()}, 
	    	function( data ) {
	    	  var strJson =strToJson(data);
	            if(strJson.result == 2){
	            	setUserInfo(strJson.username);
	            	$('#loginModal').modal('hide'); 
	            } 
	            else if(strJson.result == 1) {
	                 shakeModal('密码不正确！'); 
	            }
	            else  if(strJson.result == 0){
	            	shakeModal('用户不存在！');
				}	            
	        });
		}

}

function registAjax(){
	var _username=$('#username-r').val();
	if(_username=='')
	{
		shakeModal('请输入用户名！');
		return ;
	}
	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
	var email=$('#email').val();
	if (email=='') {
		shakeModal('请输入邮箱！');
		return ;
	}
	else if (!reg.test(email)) {
		shakeModal('请输入正确的邮箱！');
		return ;
	}
	if ($('#password-r').val()=='')
	{
		shakeModal('请输入密码！'); 
		return ;
	}
	if ($('#password_confirmation').val()=='')
	{
		shakeModal('请确认密码！'); 
		return ;
	}
	if ($('#password_confirmation').val()!=$('#password-r').val())
	{
		shakeModal('确认密码错误！'); 
		return ;
	}
	$.post( "regist", 
    		{username:_username,
		email:$('#email').val(),
    	password:$('#password-r').val()}, 
    	function( data ) {
    	  var strJson =strToJson(data);
            if(strJson.result == 1){
               alert("注册成功，开始使用吧！");
               $('#loginModal').modal('hide'); 
               openLoginModal();    
            } 
            else  {
                 shakeModal('注册失败，请重试或联系系统管理员！'); 
            }
                       
        });
}
 function setUserInfo(username) {
	 $('#user-info').html(
			 '<span>欢迎：'+username+'&nbsp&nbsp</span>'+
			 '<a href="javascript:void(0)" onclick="newPath()">新建配送</a>'+
				'<span>&nbsp&nbsp</span>'+
				'<a  href="javascript:void(0)" onclick="historyPath()">历史配送</a>'+
				'<span>&nbsp&nbsp</span>'+
				'<a  href="javascript:void(0)" onclick="logout()">退出</a>'+
				'<span>&nbsp</span>').css('display','block');
	 $('#user-login').css('display','none');
}
function shakeModal(massage){
     $('#loginModal .modal-dialog').addClass('shake');
	 $('.error').addClass('alert alert-danger').html(massage);
	 $('input[type="password"]').val('');	 
	 setTimeout(function(){ 
		 $('#loginModal .modal-dialog').removeClass('shake');
		 $('.error').removeClass('alert alert-danger').html('');
		 }, 800); 
}

   