The "core" packages contains the central classes and should *not* be modified 
in *any* way unless you know what you're doing. Modifications may impact on 
all other packages.

The "core" package contains two sub-packages :
 - core.representation package : representation-related classes (population, 
   individual, etc.). 
 - core.evolution package : classes used for evolution (operators for initialisation, 
   variations, evaluations, etc.).

Extensions should be written in the "ext" package.

Please refer to the "tutorial" package for examples.
