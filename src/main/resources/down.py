import requests
import sys, getopt

def main(argv):
   inputfile = ''
   try:
      opts, args = getopt.getopt(argv,"hi:o:",["ifile="])
   except getopt.GetoptError:
      print("test.py -i <inputfile>")
      sys.exit(2)
   for opt, arg in opts:
      if opt == '-h':
         print('test.py -i <inputfile>')
         sys.exit()
      elif opt in ("-i", "--ifile"):
         inputfile = arg
		 
   URL = "https://hudoc.echr.coe.int/app/conversion/docx/?library=ECHR&id=" + inputfile + "&filename=" + inputfile + ".docx"
   response = requests.get(URL)
   print("doc ", response.status_code)
   open(inputfile + ".docx", "wb").write(response.content)
   
   URL = "https://hudoc.echr.coe.int/app/conversion/docx/html/body?library=ECHR&id=" + inputfile
   response = requests.get(URL)
   print("html ", response.status_code)
   open(inputfile + ".html", "wb").write(response.content)

if __name__ == "__main__":
   main(sys.argv[1:])
