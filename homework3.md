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

- requires: `source` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$,  `target` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - returns true if $`(\mathsf{source}, \mathsf{target}) \in E_{this}`$
  - returns false, otherwise.

##### getSources

```java
Set<N> getSources(N target);
```

- requires: `target` is in $`\mathcal{N}`$, not $`\mathsf{null}`$, and in $`V_{this}`$. 
- ensures: 
  - returns set of $`v_i`$, where $`v_i \in V_{this}`$ and $`(v_i, \mathsf{target}) \in E_{this}`$.
    - If there is no $`v_i`$ that meets the conditions, it may be an empty set.

##### getTargets

```java
Set<N> getTargets(N source);
```

- requires: `source` is in $`\mathcal{N}`$, not $`\mathsf{null}`$, and in $`V_{this}`$. 
- ensures:
  - returns set of $`v_i`$, where $`v_i \in V_{this}`$ and $`(\mathsf{source}, v_i) \in E_{this}`$.
    - If there is no $`v_i`$ that meets the conditions, it may be an empty set.


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\ v, w \in V_{this}, \textsf{ for all } (v, w) \in E_{this} \\
(v_i, \hat{v}_{this}) \notin E_{this}, \textsf{ for all } v_i \in V_{this} \\
|\{v_j | (v_j, v_i) \in E_{this} \textsf { where } v_j \in V_{this}\}| = 1, \textsf{ for all } v_i \in V_{this} \\
|\{{p_0, p_1, \cdots, p_k | (p_i, p_{i+1}) \in E_{this}, 0 \leq i < k}\}| = 1 \textsf{ where } p_0 = \hat{v}_{this}, p_k = v_i, \textsf{ for all } v_i \in V_{this}
```



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

- requires: 
  - getRoot.isPresent()
- ensures:  
  - returns the maximum depth in this tree.

##### getRoot

```java
Optional<N> getRoot();
```

- requires: 
  - N/A
- ensures: 
  - returns the root of this tree if tree is not empty.
  - returns `Optional.empty()` if tree is empty

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires:
  - vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
  - getRoot.isPresent()
- ensures: 
  - returns `Optional.empty()` if `vertex` is root
  - returns $`v`$ where $`(v, \textsf{vertex}) \in E_{this}`$ if `vertex` is not root




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

- requires: `vertex` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - $`V_{next} = V_{this} \setminus \{\textsf{vertex}\}`$
  - $`E_{next} = E_{this} \setminus \{(v, w) | (v=\textsf{vertex}, w\in V_{this}) \or (w=\textsf{vertex}, v\in V_{this}) \}`$
  - If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
  - returns true if $`\textsf{vertex} \in V_{this}`$
  - returns false, otherwise.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: 
  - `source` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - `target` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - $`\textsf{source} \in V_{next}`$
  - $`\textsf{target} \in V_{next}`$
  - $`(\textsf{source}, \textsf{target}) \in E_{next}`$
  - If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
  - returns false if $`(\textsf{source}, \textsf{target}) \in E_{this}`$
  - returns true, otherwise.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires:
  - `source` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - `target` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - $`(\textsf{source}, \textsf{target}) \notin E_{next}`$
  - returns true if $`(\textsf{source}, \textsf{target}) \in E_{this}`$
  - returns false, otherwise.


## `MutableTree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### Class invariant 

```math 
\ v, w \in V_{this}, \textsf{ for all } (v, w) \in E_{this} \\
(v_i, \hat{v}_{this}) \notin E_{this}, \textsf{ for all } v_i \in V_{this} \\
|\{v_j | (v_j, v_i) \in E_{this} \textsf { where } v_j \in V_{this}\}| = 1, \textsf{ for all } v_i \in V_{this} \\
|\{{p_0, p_1, \cdots, p_k | (p_i, p_{i+1}) \in E_{this}, 0 \leq i < k}\}| = 1 \textsf{ where } p_0 = \hat{v}_{this}, p_k = v_i, \textsf{ for all } v_i \in V_{this}
```



##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: `vertex` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
  - If this $`T_{this}`$ is empty,
    - $`\hat{v}_{next} = \textsf{vertex}`$ 
    - return true
  - else,
    - return false

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: `vertex` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - When $W = \textbf{vertex} \cup \{v_i | v_i \textsf{ reachable from }\textbf{vertex}\}$,
  - $`V_{next} = V_{this} \setminus W`$
  - $`E_{next} = E_{this} \setminus \{(v, w) | (v \in W, w\in V_{this}) \or (w \in W, v\in V_{this}) \}`$
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
  - returns true if $`\textsf{vertex} \in V_{this}`$
  - returns false, otherwise.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires:
  - `source` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - `target` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - if $`\textsf{source} \in V_{this}`$, $`\textsf{target} \notin V_{this}`$,
    - $`V_{next} = V_{this} \cup \textsf{target}`$
    - $`E_{next} = E_{this} \cup (\textsf{source}, \textsf{target})`$
    - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
    - returns true
  - Else,
    - returns false

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires:
  - `source` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - `target` is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - If $`(\textsf{source}, \textsf{target}) \in E_{this}`$
    - When $`W = \textbf{target} \cup \{v_i | v_i \textsf{ reachable from }\textbf{target}\}`$, 
    - $`V_{next} = V_{this} \setminus W`$
    - $`E_{next} = E_{this} \setminus \{(v, w) | (v \in W, w\in V_{this}) \or (w \in W, v\in V_{this}) \}`$
    - returns true
  - Else,
    - returns false
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant




# Problem 2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?
* **Yes.** `Tree<N>`은 `Graph<N>`의 invariant에 비해, (1) root vertex가 존재하고, (2)  root를 제외한 모든 vertex가 하나의 parent를 가지며, (3) 모든 vertex가 root로 부터 접근 가능하다는 invariant가 추가적으로 존재한다. 또한, `getDepth`, `getHeight`, `getRoot`, `getParent`에 대한 precondition들과 postcondition들이 추가적으로 필요하다. 즉, `Tree<N>` 이 더 strong하게 정의되므로, `Graph<N>`의 subtype이라고 말할 수 있다.

##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`
* **Yes.** `MutableGraph<N>` 은 `Graph<N>`에 비해 `addVertex`, `removeVertex`, `addEdge`, `removeEdge` 에 대한  대한 precondition들과 postcondition들이 추가적으로 필요하다. 즉, `MutableGraph<N>` 이 더 strong하게 정의되므로, `Graph<N>`의 subtype이라고 말할 수 있다.

##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`
  * **Yes.** `MutableTree<N>` 은 `Tree<N>`에 비해 `addVertex`, `removeVertex`, `addEdge`, `removeEdge` 에 대한  대한 precondition들과 postcondition들이 추가적으로 필요하다. 즉, `MutableTree<N>` 이 더 strong하게 정의되므로, `Tree<N>`의 subtype이라고 말할 수 있다.

##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`

  * **No.** 

    * **(i)** $`S, T \in V, (S,T) \notin E`$ 일때, `MutableTree<N>`의 `addEdge` 는 `false` 를 반환하지만, `MutableGraph<N>` 의 `addEdge` 는 `true` 를 반환한다. 즉, 둘의 동작이 다르기 때문에 Liskov substituiton principle을 위반한 것으로 해석된다.

    * **(ii)** 아래 코드 예시에서, `graph.removeVertex(v3);` 이 호출되면 `v3` 만이 삭제되지만,  `MutableTree<N>` 에서는 `v3`, `v4`, `v5` 가 삭제되어 다른 행동을 보인다.

      ```java
      MutableGraph<V> graph;
      V v1, v2, v3, v4, v5;
      
      graph.addEdge(v1, v2);
      graph.addEdge(v2, v3);
      graph.addEdge(v3, v4);
      graph.addEdge(v4, v5);
      
      graph.removeVertex(v3);
      ```
