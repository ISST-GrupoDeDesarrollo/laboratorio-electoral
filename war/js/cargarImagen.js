function cargarFoto() {
      			$( "#imgInp" ).trigger( "click" );
        	}
            function cargarImg(input) {
        		if (input.files && input.files[0]) {
            	var reader = new FileReader();
            	reader.onload = function (e) {
                	$('#fot').attr('src', e.target.result);
            	}

            reader.readAsDataURL(input.files[0]);
        		}
    		}