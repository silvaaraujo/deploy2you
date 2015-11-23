(function($) {
	
	alerta = {};

	alerta.show = function(msg) { 
		$.bootstrapGrowl(msg);
	};
	
	alerta.info = function(msg) { 
		$.bootstrapGrowl(msg, {
			type: 'info'
		});
	};
	
	alerta.sucesso = function(msg) { 
		$.bootstrapGrowl(msg, {
			type: 'success'
		});
	};
	
	alerta.erro = function(msg) { 
		$.bootstrapGrowl(msg, {
			type: 'danger'
		});
	};
	
	alerta.aviso = function(msg) { 
		$.bootstrapGrowl(msg, {
			type: 'warning'
		});
	};

})(jQuery);