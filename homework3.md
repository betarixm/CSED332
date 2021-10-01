# Problem 1


Let $`\mathcal{N}`$ be the set of all elements of type $`\textsf{N}`$, and $`\mathsf{null} \notin \mathcal{N}`$ be an distinguished element to represent `null`. Write formal abstract specifications of the interfaces below with respect to following abstract values:

- A graph is a pair $`G = (V, E)`$, where $`V \subseteq \mathcal{N}`$ and $`E \subseteq V \times V`$.
- A tree is a triple $`T = (V, E, \hat{v})`$, where $`(V, E)`$ is a graph and $`\hat{v} \in \mathcal{N}`$ denotes the root.

Other data types, such as `boolean`, `int`, `Set<N>`, etc. have conventional abstract values, e.g., Boolean values, integers, and subsets of $`\mathcal{N}`$, etc.

## `Graph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### containsVertex

```java 
boolean containsVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if vertex is in $`V_{this}`$; and
    - returns false, otherwise.

##### containsEdge

```java
boolean containsEdge(N source, N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### getSources

```java
Set<N> getSources(N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### getTargets

```java
Set<N> getTargets(N source);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

<!-- TODO -->

##### getDepth

```java
int getDepth(N vertex);
```

- requires: 
  + vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
  + getRoot.isPresent()
- ensures:  
  + returns 0 if vertex is getRoot.get(); and
  + returns getDepth(getParent(vertex)) + 1, otherwise.

##### getHeight

```java
int getHeight();
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### getRoot

```java
Optional<N> getRoot();
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->


## `MutableGraph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object,
and $`G_{next} = (V_{next}, E_{next})`$ be an abstract value of the graph object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} \cup \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->


## `MutableTree<N>`


Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### Class invariant 

<!-- TODO -->

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: <!-- TODO -->
- ensures:  <!-- TODO -->


# Problem 2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?
<!-- TODO -->

##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`
<!-- TODO -->

##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`
<!-- TODO -->

##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`
<!-- TODO -->
