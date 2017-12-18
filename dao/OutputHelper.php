<?php

    require_once 'dao/Connection.php';
    
class OutputHelper{

    
    
    /** print all points as table 
     * 
     * @param type $pointArray - array of points for print 
     * @param type $countInRow - count of points in row 
     * @param type $styleOdd - style of x*2 element
     * @param type $styleNotOdd - style of x*2+1 element
     * 
     */
    public static function printPoints($pointArray, $countInRow, $styleOdd, $styleNotOdd){
        echo "<table id=\"result_table\"> ";
        echo "   <tr>";
        for($counter=0;$counter<count($pointArray);$counter++){
            if( $counter!=null &&  $counter%$countInRow==0 ){
                echo "</tr> <tr>";
            }
            $onClickEvent="onclick=\"point_click(".$pointArray[$counter]->pos_x.", ".$pointArray[$counter]->pos_y.")\"";
            $onMouseOver="onmouseover=\"point_mouseover('".$pointArray[$counter]->pointnum."')\"";
            // $onClickEvent="";
            if($counter%2==0){
                echo "<td align=\"center\" class=\"$styleOdd\"  $onClickEvent $onMouseOver>";
            }else{
                echo "<td align=\"center\" class=\"$styleNotOdd\"  $onClickEvent $onMouseOver>";
            }
            
            $pointCaption= str_pad($pointArray[$counter]->pointnum,3,"&");
                    
            echo "<b> ". str_replace("&", "&nbsp;", $pointCaption) ." </b> ";
            echo "</td>";
        }
        echo "   </tr>";
        echo "</table> ";
    }
    
    
    public static function printJavaScriptPointsInfo($arrayOfPoints, $varName){
        // preambula
        echo " <script type=\"text/javascript\" > ";
        echo " var ". $varName." = {";
        
        $flagFirstPoint=true;
        foreach ($arrayOfPoints as $eachPoint) {
            // check for print not first point 
            if($flagFirstPoint){
                $flagFirstPoint=false;
            }else{
                echo ", ";
            }
            
            echo $eachPoint->pointnum." : ".  OutputHelper::getCommodityAsString($eachPoint);
        }
        
        // postambula 
        echo "}";
        echo " </script> ";
    }
    
    public static function printJavaScriptPointsHtml($arrayOfPoints, $varName){
        // preambula
        echo " <script type=\"text/javascript\" > ";
        echo " var ". $varName." = {";
        
        $flagFirstPoint=true;
        foreach ($arrayOfPoints as $eachPoint) {
            // check for print not first point 
            if($flagFirstPoint){
                $flagFirstPoint=false;
            }else{
                echo ", ";
            }
            
            echo $eachPoint->pointnum." : \"".  $eachPoint->html."\"";
        }
        
        // postambula 
        echo "}";
        echo " </script> ";
    }
    
    private static function getCommodityAsString($eachPoint){
        // preambula
        $commodityAsString="\" <ul> ";
        // print commodity(ies) of point
        $flagFirstCommodity=true;
        if( isset($eachPoint->commodity) && count($eachPoint->commodity)>0 ){
            foreach($eachPoint->commodity as $eachCommodity){
                if($flagFirstCommodity){
                    $flagFirstCommodity=false;
                }else{
                     $commodityAsString.="<hr>";
                }
                $commodityAsString.="<li>".$eachCommodity." </li>";
            }
        }

        $commodityAsString=$commodityAsString."</ul>\"";
        // postambula
        return $commodityAsString;
    }

    
    public static function printPopular($pathToFile){
        $lines=file($pathToFile, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
        foreach($lines as $eachLine){
            // <div class="prepare_result_element">компьютерные комплектующие</div>
            echo "<div class=\"prepare_result_element\">$eachLine</div>";
        }
    }
    
    public static function printNotFound(){
        echo " <div align=\"center\"> <b> NOT FOUND </b>  </div>";
    }

    
    public static function isAllowsBrowser(){
        // $browserData=  get_browser(null, true);
		if (get_cfg_var('browscap'))
			$browserData=get_browser(null, true); //If available, use PHP native function
		else{
		 require_once('php-local-browscap.php');
		 $browserData=get_browser_local(null, true,'php_browscap.ini');
		}
        return stristr($browserData['browser'], "Firefox") 
                || stristr($browserData['browser'], "Chrome")
                || stristr($browserData['browser'], "Epiphany")
                || stristr($browserData['browser'], "Opera")
            ;
    }
    
}

?>
