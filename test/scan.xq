declare namespace dt="java:apb.modules.DocType";
declare variable $base:="C:\Users\abunce\Dropbox\cup\workspace\dev\model\";
for  $f in file:list($base,true(),"ex*.xml")
let $dtd:=  try {
   dt:loadXMLFromFile($base || $f)
 } catch *
 {
   <err file="{$f}"/>
 }
return $dtd
