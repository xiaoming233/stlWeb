function newPath(){
		$('#new-path').css('display','block');
		$('#history').css('display','none');
		clearOverlays();
	}
function historyPath(){
	$("#new-path").css("display","none");
	$("#history").css("display","block");
	$.get("history",function(data,status){
		if (status=='success') {
			var resultData=strToJson(data);
			if (resultData.status==1) {
				var result=resultData.result;
				var n=result.length
				$('#history-path').html('');
				if (n>0) {
					var ol=document.createElement("ol");
					ol.id='result_ol';
					for (var i = 0; i <n ; i++) {
						var li=document.createElement("li");
						li.id=result[i].id;
						li.innerHTML ="<a href=\"javascript:void(0)\">"+result[i].path+"</a>";
						ol.appendChild(li); 
					}					
					$('#history-path').append(ol);
					$("#result_ol").on("click","li",function(){      //只需要找到你点击的是哪个ul里面的就行
						doTspbyGet(this.id)
					 });
				} else {
					$('#history-path').html('<p>没有历史配送记录！</p>');
				}
				
			} else {
				openLoginModal();
			}
		} else {
			alert("Status: " + status);
		}
	  });
}
function logout() {
	con=confirm("你真的要退出么?"); //在页面上弹出对话框
	if(con==true){
	$.get("history",function(data){
		if (data) {	
			 $('#user-info').css('display','none');
			 $('#user-login').css('display','block');
			 newPath();
		}			
	  });
	}
}