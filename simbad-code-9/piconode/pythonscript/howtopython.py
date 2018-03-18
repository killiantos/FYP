#script xml sax
#This script read an xml file and create a Simplenet Topology

#Robotaoviusal...  ../jython-2.1/jython pythonscripts/howtopython.py 

#Importing the simplenet library
from piconode import *
from xml.sax import saxutils

#Creating a network while receiving sax events
class CreateNetwork(saxutils.DefaultHandler):
	listNeuron =[] 
	reseau = Network(LogisticSigmoidActivationFunction() )		
	
	def startElement(self, name, attrs):
		print name 
		#Create a network
		
    		 
		
			
			
        # Look for the title and number attributes (see text)
		if name=='node' :
			neuronename = attrs.get('name', None)
			print 'nodename :' + neuronename
			#Create a node with type and values
			self.listNeuron.append(Neuron(self.reseau,LogisticSigmoidActivationFunction()))
			#Switch to append to network with correct type (in out ...)
			if (type==1)
				self.reseau.registerInputNeuron(self.listNeuron[-1])
			else if (type==2)
				self.reseau.registerOutputNeuron(self.listNeuron[-1])
		if name=='link' :
			#Neurons needs to be found via order in the list
			neuroneStart = attrs.get('from', None)
			neuroneStop = attrs.get('to', None)
			
			self.reseau.registerStdArc( Arc( self.listNeuron[int(neuroneStart)] ,self.listNeuron[int(neuroneStop)] , 0 ));


		
			
from xml.sax import make_parser
from xml.sax.handler import feature_namespaces

if __name__ == '__main__':
    # Create a parser
    parser = make_parser()

    # Tell the parser we are not interested in XML namespaces
    parser.setFeature(feature_namespaces, 0)

    # Create the handler
    dh = CreateNetwork()

	#adding values etc.

    # Tell the parser to use our handler
    parser.setContentHandler(dh)


    # Parse the input
    parser.parse(open('/Users/lri/neurone.xml', 'r'))




