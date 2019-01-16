# MortgageApplication2
IBM Sample Mortgage Application from DBB using zJenkins. See https://github.com/ibmdbbdev/Samples/tree/v1.0.1/MortgageApplication/build

Sample application which calls zJenkins to build and deploy, to AppDev only, Mortgage Application

## Module Selection
* ./conf/package.txt supports generics to build selection list of which modules to build
    * Sample package.txt will select all packages starting with com/ibm/*
    * However, it will bypass any packages not defined in the zJenkins FileTypeMap.properties
		
				* '**/asm/**/*.asm'	for Assembler source
				* '**/bms/**/*.bms'	for BMS Maps
				* '**/cicsapi/*.cicsapi'	for CICS Native API configuration
				* '**/cicsws/*.cicsws'	for CICS WebService configuration
				* '**/cobol/**/*.cbl'	for Cobol source code
				* '**/compileOnly/**/*.cbl'	for Cobol compile only source code
				* '**/copybook/*.cpy'	for Cobol Copybook source code
				* '**/cobol/**/*.dual'	for Cobol Dual (Batch and Online) source code
				* '**/easytrieve/**/*.ezt'	for Easytrieve source code
				* '**/jcl/*.jcl'	for JCL 
				* '**/linkedit/*.lnk'	for Object module Linkedit only
				* '**/mfs/*.mfs'	for IMS MFS source generation
				* '**/rexx/**/*.rexx	for REXX source code
				* '**/sdfii/*.sdf	for Screen Definition Faciliy source code generation
				
		* This configuration of com/ibm/* will bypass packages like com/ibm/NoBuild
		* Routines mfs, rexx and sdfii still need work
		* for am, bms, cobol, compileOnly, cobol/dual, and rexx have an extra ** in the selection list to allow for multiple configuration when building the source code
			- for example:
					- com/ibm/cobol/App1 could be compiled with Cobol v6
					- com/ibm/cobol/App2 could be compiled with Cobol v4.2
				- These configuration or properties are read from the project properties file, in this case MortgageApplication.properties
				- By default, zJenkins will look for the 'Jenkins Project name'.properties
