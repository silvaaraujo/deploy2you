(function($) {
	
	alerta = {};

	alerta.show = function(msg) { 
		$.bootstrapGrowl(msg);
	};

})(jQuery);

