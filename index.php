<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Радиорынок метро Харьковская</title>
        <meta name="keywords" content="радиорынок, харьковский, метро Харьковская, дарницкий, Киев" />
        <meta name="description" content="Радиорынок в Киеве, Радиорынок на Харьковском, Радиорынок на метро Харьковская, Радиорынок возле метро Харьковская, Торговые точки и описание товара на них. Карта точек. Карта проезда. Режим работы" />
        <meta name="author" content="cherkavi" />
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="expires" content="fri, 04 oct 2013 00:30:00 GMT" />
        <!-- css for this file -->
        <link rel="stylesheet" href="index.css" type="text/css" media="screen, projection" />

        <!--  Common library  -->
        <!-- <script type="text/javascript" src="scripts/jquery.js"></script> -->
        <script type="text/javascript" src="scripts/jquery-1.9.1.js"></script>

        <!-- Zoom library -->
        <script type="text/javascript" src="scripts/finezoom2.js"></script>
        <link rel="stylesheet" href="css/style_zoom.css" type="text/css" />

        <script type="text/javascript" src="scripts/jquery.fancybox.pack.js"></script> 
        <link rel="stylesheet" type="text/css" href="css/jquery.fancybox.css" />

        <!-- autocomplete -->
        <script type="text/javascript" src="scripts/jquery.ui.core.js"></script> 
        <script type="text/javascript" src="scripts/jquery.ui.widget.js"></script> 
        <script type="text/javascript" src="scripts/jquery.ui.position.js"></script> 
        <script type="text/javascript" src="scripts/jquery.ui.menu.js"></script> 
        <script type="text/javascript" src="scripts/jquery.ui.autocomplete.js"></script> 
        
        <!-- avoid conflict between different type of Javascript library such as Mootool and jQuery -->
        <script type="text/javascript">
            // use "jQuery" instead of "$"
            $.noConflict();
            // additional functions add to classes
                // String.trim
            String.prototype.trim=function(){return this.replace(/^\s+|\s+$/g, '');};
        </script> 

        <!-- modal window, mootools -->
        <link rel="stylesheet" href="scripts/assets/css/simplemodal.css" type="text/css" media="screen" title="no title" charset="utf-8">
        <script src="scripts/mootools-core-1.4.5.js" type="text/javascript" charset="utf-8"></script>
        <script src="scripts/mootools-more-1.4.0.1.js" type="text/javascript" charset="utf-8"></script>
        <script src="scripts/simple-modal.js" type="text/javascript" charset="utf-8"></script>
        

        <link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
        <style>
          .ui-autocomplete-loading {
            background: white url('css/images/animated-overlay.gif') right center no-repeat;
          }
        </style>
    </head>

    <body>
        <?php
            require_once 'dao/CommodityFinder.php';
            require_once 'dao/OutputHelper.php';
            require_once 'dao/Point.php';

            if(OutputHelper::isAllowsBrowser()==false){
                ?>
        <div class="not_allow">
            <table>
                <tr>
                    <td colspan="4"> 
                        <center>
							<h1>Радиорынок в Киеве</h1> <br />
                            <h2> ( Радиорынок на Харьковском, Радиорынок на метро Харьковская )</h2>
                        </center>
                        <br />
                    </td>
                </tr>
                <tr>
                    <td colspan="4"> 
                        <center>
                            <h2>Карта проезда, режим работы, карта точек <br/>
                                Поиск товара на точках и контакты с этими точками</h2>
                        </center>
                    </td>
                </tr>
                <tr>
                    <td colspan="4"> 
                        <center>
                            <hr  />
                        </center>
                    </td>
                </tr>
                <tr>
                    <td colspan="4"> 
                        <center>
                            <h3> Ваш браузер не поддерживается, <br />
                                пожалуйста, перейдите к одному из <br/>
                                перечисленных в списке: </h3>
                        </center>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="allow_browser">
                            <a href="http://mozilla.org.ua/">
                                <image src="images/browsers/Firefox_logo.png" />
                            </a>
                        </div>
                    </td>
                    <td>
                        <div class="allow_browser">
                            <a href="http://chrome.google.com">
                                <image src="images/browsers/Chrome_logo.jpg" />
                            </a>
                        </div>
                    </td>
                    <td>
                        <div class="allow_browser">
                            <a href="http://www.opera.com">
                                <image src="images/browsers/Opera_logo.jpg" />
                            </a>
                        </div>
                    </td>
                    <td>
                        <div class="allow_browser">
                            <a href="https://projects.gnome.org/epiphany/">
                                <image src="images/browsers/Epiphany_logo.png" />
                            </a>
                        </div>
                    </td>

                </tr>
            </table>
        </div>
    </body>
                <?php
                exit();
            }
        
            // check input variable
            if(isset($_REQUEST['search_commodity'])){
                $requestCommodity=$_REQUEST['search_commodity'];
            }
            
            if(isset($requestCommodity)){
                $requestCommodity=trim($requestCommodity);
                if(strlen($requestCommodity)>4){
                    $finder = new CommodityFinder;
                    $pointIds = $finder->getPointsByCommodity($requestCommodity);
                    $pointArray=array();
                    if(count($pointIds)>0){
                        foreach( $pointIds as $eachPointId){
                            array_push($pointArray, DbHelper::getPointById($eachPointId) );
                        }
                    }
                }else{
                    // not_found
                    $notFound=true;
                }
            }else{
                // default behavior
            }
        ?>

        
        <div id="header_body">

            <div id="header">
                <div id="header_image" >
                    <image src="images/market_logo.png" >
                </div>
                <div id="header_menu">
                    <table>
                        <tr>
                            <td >
                                <a id="menu_map" href="images/Kiev_map.jpg">
                                    <image id="header_menu_map" src="images/menu_map.png" > 
                                </a>
                            </td>
                            <td>
                                <image id="header_menu_days" src="images/menu_schedule.png"> 
                            </td>
                            <td>
                                <image id="header_menu_contacts" src="images/menu_contacts.png"> 
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="body">
                <!-- #container-->
                <div id="panel_map">
                    <div id="image_container">
                        <!-- #content-->
                        <image id="document_image" 
                               src="images/Map_main2.jpg" 
                               unselectable="on"
                               style="float:left; width:100%; ">
                            <!-- style="height: 450px; width: 650px;" -->
                    </div>
                </div>

                <div id="panel_info" >
                    <div id="panel_search" >
                        <form id="search_form" method="POST">
                            <table style="width:100%">
                                <tr>
                                    <td align="center">
                                        <input type="text" id="search_commodity" name="search_commodity" title="введите товар для поиска" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input type="submit" id="search_button" value="Поиск" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    
                    <?php 
                        if(isset($pointArray) && count($pointArray)>0 ){
                            // print panel with information 
                    ?>
                    <div id="panel_result">

                        <div id="panel_result_links">
                            <?php
                                if(isset($pointArray) && count($pointArray)>0){
                                    OutputHelper::printPoints($pointArray, 3, "result_cell_odd", "result_cell");
                                    OutputHelper::printJavaScriptPointsInfo($pointArray, "point_info_array");
                                    OutputHelper::printJavaScriptPointsHtml($pointArray, "point_html_array");
                                }else{
                                    if(isset($notFound)){
                                        OutputHelper::printNotFound();
                                    }else{
                                        // first load
                                    }
                                }
                            ?>
                        </div>
                        
                        <div id="panel_result_quick_info">
                            <div id="panel_result_quick_info_text">
                            </div>
                        </div>

                    </div>
                    <?php
                        }else{
                            // print popular commodity 
                            echo "<div id=\"panel_prepare_result\">";
                            OutputHelper::printPopular('scripts/popular_commodity.txt');
                            echo "</div>";
                        }
                    ?>
                </div>
            </div>

        </div>


        <!-- after load document script -->
        <script type="text/javascript" >
                                /** original image size: width  */
                                var koef_x = 4102;
                                /** original image size: height  */
                                var koef_y = 3460;
                                /** shift for horizontal position of marker for map */
                                var shift_x = -15;
                                /** shift for vertical position of marker for map */
                                var shift_y = -30;

                                /** initialize ZOOM algorithm for certain image  */
                                function init_zoom(image_selector) {
                                    // styleClass: "additional_css_class",
                                    jQuery(image_selector).finezoom({
                                        markers: [
                                                  <?php 
                                                    if(isset($pointArray) && count($pointArray)>0){
                                                        foreach($pointArray as $eachPoint){
                                                            echo "{";
                                                            echo "icon: null,";
                                                            echo "top: $eachPoint->pos_y * koef_y + shift_y,";
                                                            echo "left: $eachPoint->pos_x * koef_x + shift_x,";
                                                            echo "mouseenter: function(){pointmarker_mouse_enter('$eachPoint->pointnum')},";
                                                            echo "mouseleave: function(){pointmarker_mouse_leave('$eachPoint->pointnum')},";
                                                            echo "click: function(){pointmarker_mouse_click('$eachPoint->pointnum')}";
                                                            echo "},        ";
                                                        }
                                                    }
                                                  ?>
                                                ]
                                    });
                                }

                                /** set image size - width and height  */
                                function set_image_size() {
                                    var image_selector = '#document_image';
                                    // height
                                    var heightMax = jQuery(window).height() - jQuery('#header').height() - 1;
                                    if (heightMax < jQuery(image_selector).height()) {
                                        var koef = jQuery(image_selector).width() / jQuery(image_selector).height();
                                        jQuery(image_selector).height(heightMax);
                                        jQuery(image_selector).width(heightMax * koef);
                                    } else {
                                        jQuery(image_selector).height(jQuery(image_selector).height());
                                        jQuery(image_selector).width(jQuery(image_selector).width());
                                    }
                                    jQuery('#panel_info').height(jQuery(image_selector).height());

                                    // width
                                    var widthMax = jQuery('#panel_map').width();
                                    if (widthMax > jQuery(image_selector).width()) {
                                        var panelWidth = jQuery('#panel_map').width();
                                        var shift = (widthMax - jQuery(image_selector).width()) / 2;
                                        jQuery('#image_container').css("margin-left", (shift) + "px");
                                        jQuery('#image_container').width(panelWidth - shift);
                                    }
                                    koef_x = jQuery(image_selector).width() / koef_x;
                                    koef_y = jQuery(image_selector).height() / koef_y;
                                    init_zoom('#document_image');
                                }

                                // execute just after load all elements 
                                window.onload = function(){
                                    set_image_size();
                                    setToCenter('#header_image', '#header_image img');
                                };
                                // set image to center
                                function setToCenter(selectorParentElement, selectorElement){
                                        var containerWidth = jQuery(selectorParentElement).width();
                                        var shift = (containerWidth - jQuery(selectorElement).width()) / 2;
                                        jQuery(selectorElement).css("margin-left", (shift) + "px");
                                }
                                // set refresh when resize window
                                window.onresize = function() {
                                    location.reload();
                                };

                                // <input type="text" id="text_search" />
                                // <button type="button" id="button_init_search" >Поиск</button>

                                // init autocomplete 
                                jQuery("#search_commodity").autocomplete({
                                    source: "autocomplete.php",
                                    minLength: 2,
                                    open: function() {
                                        jQuery(this).removeClass("ui-corner-all").addClass("ui-corner-top");
                                    },
                                    close: function() {
                                        jQuery(this).removeClass("ui-corner-top").addClass("ui-corner-all");
                                    }
                                    // select: function (event, item ){jQuery('#search_button').click();},
                                });
                                
                                // init click on pre-selected search text
                                jQuery(".prepare_result_element").click(
                                        function(e){
                                            var target = e.delegateTarget; // (e.target) ? e.target : e.srcElement;
                                            jQuery('#search_commodity').attr("value", target.textContent);
                                            // alert(target.textContent);
                                            jQuery('#search_button').click();
                                        }
                                );
                                
                                // init enter key as click to search button 
                                jQuery("#search_commodity").keypress(function(e) {
                                    if (e.which == 13) {
                                        jQuery('#search_button').click();
                                    }
                                });
                                jQuery("#header_menu_days").click(function(e){
                                    var SM = new SimpleModal({"width": 600});
                                    SM.show({
                                            "title":"Расписание работы",
                                            "model":"modal-ajax",
                                            "param":{
                                                "url":"images/Schedule.png",
                                                "onRequestComplete": function(){ }
                                            }                                          
                                          });                                    
                                });
                                jQuery("#header_menu_contacts").click(function(e){
                                    var SM = new SimpleModal({"width": 600});
                                    SM.show({
                                            "title":"Контакты",
                                            "model":"modal-ajax",
                                            "param":{
                                                "url":"points/contacts.html",
                                                "onRequestComplete": function(){ }
                                            }                                          
                                          });                                    
                                });

                                // part of external API
                                function point_mouseover(pointnum){
                                    jQuery('#panel_result_quick_info_text').html(point_info_array[pointnum]);
                                }
                                
                                function point_click(real_x, real_y){
                                    jQuery('#document_image').finezoom('focusTo', real_x*koef_x, real_y*koef_y,  4);
                                }

                                // onmouse over point, onmouse enter point 
                                function pointmarker_mouse_enter(pointnum){
                                    point_mouseover(pointnum);
                                }

                                function pointmarker_mouse_leave(pointnum){
                                    jQuery('#panel_result_quick_info_text').html('');
                                }
                                
                                // onmouse click point
                                function pointmarker_mouse_click(pointnum){
                                    if(point_html_array[pointnum].trim().length===0){
                                        var SM = new SimpleModal();
                                        SM.addButton("Закрыть окно", "btn primary", function(){this.hide();});
                                        SM.show({
                                                "title":"Торговая точка : "+pointnum,
                                                "model":"modal",
                                                "contents":point_info_array[pointnum],
                                              });                                    
                                    }else{
                                        var SM = new SimpleModal({"width":640});
                                        SM.addButton("Закрыть окно", "btn primary", function(){this.hide();});
                                        SM.show({
                                                "title":"Торговая точка : "+pointnum,
                                                "model":"modal-ajax",
                                                "param":{
                                                    "url":point_html_array[pointnum]
                                                }                                          
                                              });                                    
                                    }
                                    // jQuery('#document_image').finezoom('focusTo', real_x*koef_x, real_y*koef_y,  4);
                                }
           
                                jQuery(document).ready(function(){
                                   	jQuery("a#menu_map").fancybox({
                                            'hideOnContentClick': true
                                        }); 
                                });
        </script>
    </body>
</html>
