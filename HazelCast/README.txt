

1. Implement Kryo serializer - 'cause it's in two time faster than Java standard and in the box without any optimization on 30% compact
2. Extract distributed part as Interface - and implement some solutions. One of them was implemented it's Zookeeper.
   Think about
3. Add some extra parameters for filter in web.xml - it's concerns about cookie saving and tracking on the client side
4. Some questions about deleting values from zookeeper