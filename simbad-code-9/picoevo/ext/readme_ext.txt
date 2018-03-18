The "ext" packages contains extensions of the "core" package and may be extended (more
than modified) to add new functionalities (e.g. new representations, new operators).
Modifications may impact on itself (inter-dependent classes) and the "app" packages.

The "ext" package contains two sub-packages (in the same fashion than the "core" package) :
 - ext.representation package : representation-related classes (population, 
   individual, etc.). 
 - ext.evolution package : classes used for evolution (operators for initialisation, 
   variations, evaluations, etc.).

Extensions may also be written in the "app" package if too specialised and/or app-dependent.

Please refer to the "tutorial" package for examples.
