<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="jquerysamples" />
<cq:includeClientLib categories="clientlib.weather"/>
<div class="weather-title" data-sly-test="${wcmmode.edit || wcmmode.design}">
<% String title = properties.get("title", "Search Weather");%>
<html>
<head>
<title>Weather Report</title>
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- web font -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'><!--web font-->
<link href='//fonts.googleapis.com/css?family=Lora:400,400italic,700,700italic' rel='stylesheet' type='text/css'>
<!-- //web font -->
<!-- js -->
<script src="js/jquery-1.9.1.min.js"></script> 
<script src="js/owl.carousel.js"></script>
<script>
	$(document).ready(function() { 
		$("#owl-demo").owlCarousel({

		  autoPlay: 3000, //Set AutoPlay to 3 seconds
	 
		  items : 6,
		  itemsDesktop : [640,5],
		  itemsDesktopSmall : [414,4]
	 
		});
	 
	}); 
</script>
    <script>
$(document).ready(function() {
      
    $('body').hide().fadeIn(5000);

$('#submit').click(function() {
     var failure = function(err) {
         alert("Unable to retrive data "+err);
          
    };
     var city= $('#city').val() ;  


    $.ajax('/bin/searchWeatherInfo', {
        dataType: "text",
        //data:'city='+ city,
        data:'city='+city+'&days='+5,
        success: function(rawData) {

		data=$.parseJSON(rawData);
          $("#temprC").html(data.current.temp_c+" C");
                 $("#loc").html(data.location.name);
            	var weatherInfo = "<br/><br/>"
		                		+ " Location: " + data.location.name
		                        + "<br/> Region:  " +data.location.region
		                        + "<br/> Country: " +data.location.country
		                        + "<br/><br/>";


        var tempByDate 	= '';

                $.each(data.forecast.forecastday, function(arrayIndex, eachForcastEntry) {
                	console.log(arrayIndex +" --- " +eachForcastEntry.date+ " -- " +eachForcastEntry.avgtemp_c);
                	tempByDate 	= tempByDate + " Date: " +eachForcastEntry.date
                				+ " : " +eachForcastEntry.day.avgtemp_c 
                    			+ " C,  " + eachForcastEntry.day.avgtemp_f + " F <br/>";
                });

				weatherInfo = weatherInfo + tempByDate;
				$(".weather-info").html(weatherInfo);
        },
        error: function(xhr, status, err) {
            failure(err);
        } 
    });
  });

}); // end ready
</script>
<!-- //js -->

</head>
<body onload="startTime()">
	<!-- main -->
	<div class="main">
		<h1>Weather Report</h1>


		<div class="main-wthree-row">
        </br>
            <form name="signup" id="signup">
         <table> 

           <tr>
            <td>
              
                <div><p><%=title%></p></div>
            </td>
            <td>
              <input name="city" type="text" id="city"> <input type="button" value="Search!"  class="btn btn-success" name="submit" id="submit" value="Submit">
          </td>
         </tr>  

           </table>
        </form>
    </br>
			<div class="agileits-top">

				<div class="agileinfo-top-row">
					<div class="agileinfo-top-img">

						<span> </span>
					</div>

					<h3><p id="temprC"></p><sup class="degree"></sup><span></span></h3>
					<p id="loc"></p>
					<div class="agileinfo-top-time"> 
						<div class="date-time">
							<div class="dmy">
								<div id="txt"></div>
								<div class="date">
									<!-- Date-JavaScript -->
									<script type="text/javascript">
									var mydate=new Date()
									var year=mydate.getYear()
									if(year<1000)
									year+=1900
									var day=mydate.getDay()
									var month=mydate.getMonth()
									var daym=mydate.getDate()
									if(daym<10)
									daym="0"+daym
									var dayarray=new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
									var montharray=new Array("January","February","March","April","May","June","July","August","September","October","November","December")
									document.write(""+dayarray[day]+", "+montharray[month]+" "+daym+", "+year+"")
									</script>
									<!-- //Date-JavaScript -->
								</div>
							</div> 
							<div class="clear"></div>
						</div>  
					</div>
				</div>
			</div>

			<div class="w3ls-bottom2">	 
				<div class="ac-container">
					<input id="ac-1" name="accordion-1" type="checkbox" />
					<label for="ac-1" class="grid1"> See Next Five Days </label>
					<article class="ac-small">
						<div class="wthree-grids">
							<div class="wthree-grids-row">
								<div class="weather-info"></div>
								<div class="clear"> </div>
							</div>


						</div>
					</article>
				</div>  
			</div>	

	<!-- //main --> 

	<!-- Time-JavaScript -->
	<script>
		function startTime() {
			var today = new Date();
			var h = today.getHours();
			var m = today.getMinutes();
			var s = today.getSeconds();
			m = checkTime(m);
			s = checkTime(s);
			document.getElementById('txt').innerHTML =
			h + ":" + m + ":" + s;
			var t = setTimeout(startTime, 500);
			}
			function checkTime(i) {
			if (i < 10) {i = "0" + i}; // add zero in front of numbers < 10
			return i;
		}
	</script>
	<!-- //Time-JavaScript -->
</body>
</html>