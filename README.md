Corporatique
====================

Data extraction application ([DMS]). Allows you to extract the text from diffrent filetypes (.pdf, .doc, ...)
Made in Java, has a plugin management system.

See the source of the plugins already made : [Corpoplugins]

Get the lasted release clicking on the **Download ZIP** Button

## Basic Usage
The basic usage is :
`java -jar Corporatique.jar [options] [command] [command options]`

Use `java -jar Corporatique.jar -h` or `--help` to display the command line options and commands.

####Commands
#####`execute`

Will process extractions with these options:

  * `-v` or `-d` or `--debug` will display the process in details.
  * `-f [arg]` or `--format [arg]` will force the extraction of a file with the given filetype
    * example : `java -jar Corporatique.jar execute -f pdf file.doc`
  * `-o [arg]` or `--output [arg]` will put the text in the given file. if this file already exists, it will be overwritten if the path doesn't exist, it will be created.
  * `-options [agrs]` will send the options to the plugin when executing it. These options will be processed by the plugin.

**Usage**

`java -jar Corporatique.jar execute [plugin-name] [options] [path-to-file]`

**Example**

`java -jar Corporatique.jar execute pdf file.pdf` : Will process the file with the pdf plugin the file can be in another filetype than pdf :

`java -jar Corporatique.jar execute pdf file.doc -d` : displays the debug information

`java -jar Corporatique.jar execute -format doc file.doc` : The same as the example above, but instead of specifying the plugin, we will specify the filetype

`java -jar Corporatique.jar execute file.xml` you can simply give the file, it will recognise the filetype (can fail if the extensions is changed).

`java -jar Corporatique.jar execute file.pdf -options pagedebut 0 pagefin 5` will send these options to the pdf plugin

#####`install`

Will install the given jar if it's a Corpoplugin.

options:
  * `-v` or `-d` or `--debug` will display the installation details.

**Usage**

`java -jar Corporatique.jar install [path-to-plugin.jar][options]`

**Example**

`java -jar Corporatique.jar install plugin/pdf.jar -d` : installs the pdf plugin and displays the debug information

#####`update`

Will update the plugin if it's a newer version of the installed one.

options:
  * `-v` or `-d` or `--debug` will display the update details.

**Usage**
`java -jar Corporatique.jar update [path-to-plugin.jar][options]`

**Example**

`java -jar Corporatique.jar update plugin/pdf.jar -d` : updates the pdf plugin and displays the debug information

#####`delete`

Will delete the plugin with this name, if it's installed.

options:
  * `-v` or `-d` or `--debug` will display the deletion details.

**Usage**

`java -jar Corporatique.jar delete [plugin-name][options]`

**Example**

`java -jar Corporatique.jar delete pdf -d` : deletes the pdf plugin and displays the debug information

#### Other Options
#####`--change-default` or `-c`

Will change to default the given plugin for the given filetype if the plugin is'nt already set to default and **if the filetype is supported** by the plugin.

**Usage**
`java -jar Corporatique.jar -c [plugin filetype]`

**Example**

`java -jar Corporatique.jar -c openxml docx` sets openxml as the default plugin for docx files.

#####`--details` or `-m`

Gives the details of a plugin, if the plugin is installed.

**Usage**

`java -jar Corporatique.jar -m [plugin]`

**Example**

`java -jar Corporatique.jar -m xml` displays the xml plugin details

#####``--listall`or `-l`

Lists all the installed plugins, their versions and their default and supported formats.

**Usage, Example**

`java -jar Corporatique.jar -l`

##Libraries used
**[JSPF]** 

**[jCommander]**

**[Commons IO]**
[DMS]:http://en.wikipedia.org/wiki/Document_management_system
[corpoplugins]:http://github.com/Corporatique-dev/Corporatique_plugins/
[jspf]:http://code.google.com/p/jspf/
[jcommander]:http://jcommander.org/
[Commons IO]:http://commons.apache.org/proper/commons-io/index.html
