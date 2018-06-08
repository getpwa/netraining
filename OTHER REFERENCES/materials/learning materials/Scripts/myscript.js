 function selectTheme(selectedTheme) {
    editor.setOption("theme", selectedTheme);
    document.getElementById("idBody").style.visibility = 'visible';
    }

  window.onload=function(){
     var myTextarea = document.getElementById("editor");
     editor = CodeMirror.fromTextArea(myTextarea, {
     lineNumbers: true,
     mode: "text/x-java",
 	 fixedGutter:false,
 	matchBrackets: true,
     readOnly: 'nocursor',
     });
 	editor.setSize("100%","100%");
 	}