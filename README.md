Corporatique
====================

Data extraction application ([DMS]). Allows you to extract the text from diffrent filetypes (.pdf, .doc, ...)
Made in Java, has a plugin management system.

See the source of the plugins already made : [Corpoplugins]

Get the lasted release clicking on the **Download ZIP** Button



##Usage
The basic usage is :
`java -jar Corporatique.jar [options] [command] [command options]`

There are 4 commands :
###`execute`
will process extractions with these options:

  * `-v` or `-d` or `--debug` will display the process in details.
  * `-f [arg]` or `--format [arg]` will force the extraction of a file with the given format
    * example : `java -jar Corporatique.jar execute -f pdf file.doc`
  * `-o [arg]` or `--output [arg]` will put the text in the given file. if this file already exists, it will be overwritten if the path doesn't exist, it will be created.
  * `-options [agrs]` will send the options to the plugin when executing it. These options will be processed by the plugin.

#### Usage
`java -jar Corporatique.jar execute [plugin-name] [options] [path-to-file]`

#### Example
`java -jar Corporatique.jar execute pdf file.pdf` : Will process the file with the pdf plugin the file can be in another format than pdf :

`java -jar Corporatique.jar execute pdf file.doc`

`java -jar Corporatique.jar execute -format doc file.doc` : The same as the example above, but instead of specifying the plugin, we will specify the filetype

`java -jar Corporatique.jar execute file.xml` you can simply give the file, it will recognise the filetype (can fail if the extensions is changed).

`java -jar Corporatique.jar execute file.pdf -options pagedebut 0 pagefin 5` will send these options to the pdf plugin


##Library used :
**[JSPF]** 

**[jCommander]**

**[Commons IO]**
[DMS]:http://en.wikipedia.org/wiki/Document_management_system
[corpoplugins]:http://github.com/Corporatique-dev/Corporatique_plugins/
[jspf]:http://code.google.com/p/jspf/
[jcommander]:http://jcommander.org/
[Commons IO]:http://commons.apache.org/proper/commons-io/index.html
