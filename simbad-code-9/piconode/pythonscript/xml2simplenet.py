#script xml sax
#This script read an xml file and create a Simplenet Topology

from simplenet import *
from xml.sax import saxutils

class PrintIssue(saxutils.DefaultHandler):
	def __init__(self): 
		self.neuroneQuantity=0
		
		
	def startElement(self, name, attrs):
        # If it's not a comic element, ignore it
		if name == 'node': self.neuroneQuantity = self.neuroneQuantity + 1
		print 'blabla :',self.neuroneQuantity
		
        # Look for the title and number attributes (see text)
		title = attrs.get('title', None)
		number = attrs.get('number', None)
			



			
from xml.sax import make_parser
from xml.sax.handler import feature_namespaces

if __name__ == '__main__':
    # Create a parser
    parser = make_parser()

    # Tell the parser we are not interested in XML namespaces
    parser.setFeature(feature_namespaces, 0)

    # Create the handler
	dh = PrintIssue()

    # Tell the parser to use our handler
    parser.setContentHandler(dh)

	
    # Parse the input
    parser.parse(open('/Users/lri/neurone.xml', 'r'))


#displaying the number of neurons found
print 'Nombre de neurones arrivee : ', 7
    

