package top.jeffwang;

import java.util.Hashtable;

public class LinkedList {
    Node head = null; //链表头的饮用

    /**
     * 向链表中添加值为d的节点。
     * @param d
     */
    public void addNode(int d){
        Node newNode = new Node(d);
        if (head == null){
            head = newNode;
            return;
        }
        Node tmp = head;
        while(tmp.next!=null){
            tmp = tmp.next;
        }
        tmp.next = newNode;
    }

    /**
     * 删除链表中第index个节点
     * @param index 删除第index个节点
     * @return 成功则返回True，失败则返回False
     */
    public Boolean deleteNode(int index){
        //判断index是否合理
        if(index<1||index>length()){
            return false;
        }
        //如果是删除第一个节点
        if (index == 1){
            head = head.next;
            return true;
        }
        int i = 2;
        Node preNode = head;
        Node curNode = preNode.next;
        while (curNode != null){
            if(i == index){
                preNode.next=curNode.next;
                return true;
            }
            preNode = curNode;
            curNode = curNode.next;
            i++;
        }
        return true;
    }

    /**
     * 返回链表长度，遍历一遍。
     * @return
     */
    public int length(){
        int length = 0;
        Node tmp = head;
        while (tmp != null){
            length++;
            tmp = tmp.next;
        }
        return length;
    }

    /**
     * 对链表进行排序
     * @return 排序后的头节点
     */
    public Node orderList(){
        Node nextNode = null;
        int temp = 0;
        Node curNode = head;
        while(curNode != null){
            nextNode = curNode.next;
            while(nextNode != null){
                if(curNode.data>nextNode.data){
                    temp = curNode.data;
                    curNode.data = nextNode.data;
                    nextNode.data = temp;
                }
                nextNode = nextNode.next;
            }
            curNode = curNode.next;
        }
        return head;
    }

    /**
     * 打印链表
     */
    public void printList(){
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }

    /**
     * =================================================================================================================
     * 2. 从链表中删除重复数据
     * 想法1：遍历链表，把遍历到的值存储在一个Hashtable中，若当前访问的值在Hashtable中已经存在了，说明这个数据为重复数据可以删除。
     * @param head
     */
    public void deleteDuplecate1(Node head){
        Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
        Node tmp = head;
        Node pre = null;
        while(tmp!=null) {
            if (table.containsKey(tmp.data)) {
                pre.next = tmp.next;
            } else {
                table.put(tmp.data, 1);
                pre = tmp;
            }
            tmp = tmp.next;
        }
    }
    /**
     * 想法2：对链表进行双重循环，外循环正常遍历链表，假设外循环当前遍历的结点为cur，内循环从cur开始遍历，若碰到与cur所指结点值相同，则删除这个重复结点。
     * @param head
     */
    public void deleteDuplecate2(Node head){
        Node p = head;
        while(p!=null){
            Node q = p;
            while(q.next!=null){
                if(p.data == q.data){
                    q.next = q.next.next;
                }else{
                    q = q.next;
                }
            }
            p = p.next;
        }
    }

    /**
     * =================================================================================================================
     * 3. 如何找出单链表中的倒数第k个元素
     * 想法：设置两个指针，让其中一个指针比另一个指针先前进k-1步，然后两个指针同时向前移动，循环直到先行的指针为NULL，那么后行指针就是倒数第k个元素。
     * @param head
     * @param k
     * @return
     */
    public Node findElem(Node head, int k){
        //判断k是否合理
        if (k<1){
            return null;
        }
        Node p1 = head;
        Node p2 = head;
        for (int i = 0; i <= k-1; i++){
            p1 = p1.next;
            if(p1 == null){
                return null;
            }
        }
        while(p1.next!=null){
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    /**
     * =================================================================================================================
     * 4. 如何实现链表的反转
     * 想法：在调整m的next之前，要把n保存下来，然后找到反转后链表的头结点，即next为null的尾结点
     * @param head
     */
    public void ReverseIterativaly(Node head){
        Node pReverseHead = head;
        Node pNode = head;
        Node pPrev = null;
        while(pNode!=null){
            Node pNext = pNode.next;
            if (pNext == null){
                pReverseHead = pNode;
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        this.head = pReverseHead;
    }

    /**
     * =================================================================================================================
     * 5. 如何从尾到头输出单链表
     * 使用递归的思想，要实现反过来输出链表，每访问到一个结点，先递归输出它后面的结点，再输出该结点自身。
     * @param pListHead
     */
    public void printListReversely(Node pListHead){
        if(pListHead!=null){
            printListReversely(pListHead.next);
            System.out.println(pListHead.data);
        }

    }

    /**
     * =================================================================================================================
     * 6. 如何寻找单链表的中间结点
     * 想法：双指针大法。1.两个指针同时从头开始遍历; 2.一个快指针一次走两步，一个慢指针一次走一步; 3.快指针先到链表尾部，慢指针恰好到链表中间。
     * @param head
     * @return
     */
    public Node SearchMid(Node head){
        Node p = this.head;
        Node q = this.head;
        while(p!=null&&p.next!=null&&p.next.next!=null){
            p = p.next.next;
            q = q.next;
        }
        return q;
    }

    /**
     * =================================================================================================================
     * 7. 如何检测一个链表是否有环
     * 想法：双指针大法。1. slow指针每次前进一步，fast指针每次前进两步，两个指针同时向前移动。两指针如果指向同一个值，就证明这个链表为带环单向链表。
     * @param head
     * @return
     */
    public boolean IsLoop(Node head){
        Node fast = head;
        Node slow = head;
        if (fast == null){
            return false;
        }
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                return true;
            }
        }
        return !(fast==null||fast.next==null);
    }

    /**
     * 7.1 如何寻找单链中环入口
     * 想法：假设slow指针走了s步，fast指针走了2s步，环长为r，链表长L，入口环与相遇点距离为x，起点到环入口点的距离为a时，满足表达式：
     * 2s=s+nr; s=nr; a+x=nr; a=(n-1)r+(L-a-x)
     * 环入口为L-a-x。 从链表头到环入口点等于(n-1)循环内环+相遇点到环入口点，于是在链表头与相遇点分别设一个指针，每次各走一步，两个指针必定相遇，且相遇第一个点为环入口点。
     * @param head
     * @return
     */
    public Node FindLoopPort(Node head){
        Node slow = head;
        Node fast = head;
        // 寻找相遇点
        while(fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast){
                break;
            }
        }
        if(fast == null||fast.next == null){
            return null;
        }
        // 开始寻找环入口
        slow = head;
        while(slow!=fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * =================================================================================================================
     * 8. 如何在不知道头指针的情况下删除指定结点
     * 想法：如果需要删除的是尾结点，那就删不掉。因为删除之后，不能把前结点的next置空。如果待删除的结点不是尾结点，则可以通过交换这个结点和后结点的值来删除该结点。
     * @param n
     * @return
     */
    public boolean deleteNode(Node n){
        if(n == null || n.next == null){
            return false;
        }
        int tmp = n.data;
        n.data = n.next.data;
        n.next.data = tmp;
        n.next = n.next.next;
        return true;
    }

    /**
     * =================================================================================================================
     * 9.如何判断两个链表是否相交
     * 分别遍历两个链表，记录它们的尾结点，如果它们的尾结点相同，那么这两个链表相交，否则不相交。
     * @param h1
     * @param h2
     * @return
     */
    public boolean isIntersect(Node h1, Node h2){
        if(h1==null||h2==null){
            return false;
        }
        Node tail1 = h1;
        while(tail1.next != null){
            tail1 = tail1.next;
        }
        Node tail2 = h2;
        while(tail2.next != null){
            tail2 = tail2.next;
        }
        return tail1 == tail2;
    }

    /**
     * 9.1 如何找到它们相交的第一个结点？
     * 分别计算两个链表head1, head2的长度len1, len2，接着先对链表head1遍历(len1-len2)个结点到结点p，此时结点p和head2到它们相交的结点距离相同
     * 此时，同时遍历两个链表，直到遇到相同的结点为止，这个结点就是它们相交的结点。
     * @param h1
     * @param h2
     * @return
     */
    public static Node getFirstMeetNode(Node h1, Node h2){
        if(h1 == null||h2 ==null){
            return null;
        }
        Node tail1 = h1;
        Node tail2 = h2;
        // 求两个链表的长度
        int len1 = 1;
        while(tail1.next != null){
            tail1 = tail1.next;
            len1++;
        }
        int len2 = 1;
        while(tail2.next != null){
            tail2 = tail2.next;
            len2++;
        }
        //先遍历h1到(len1-len2)的p结点
        Node t1 = h1;
        Node t2 = h2;
        if(len1-len2>0){
            int d = len1-len2;
            while(d>0){
                t1 = t1.next;
                d--;
            }
        }else{
            int d = len2 - len1;
            while(d>0){
                t2 = t2.next;
                d--;
            }
        }
        //同时遍历两个链表, 直到遇到相同的结点为止。
        while(t1!=t2){
            t1 = t1.next;
            t2 = t2.next;
        }
        return t1;
    }

    public static void main(String[] args){
        LinkedList list = new LinkedList();
        list.addNode(3);
        list.addNode(1);
        list.addNode(5);
        list.addNode(3);
        list.deleteNode(1);
        System.out.println("listLen="+list.length());
        System.out.println("before order:");
        list.printList();
        list.orderList();
        System.out.println("after order:");
        list.printList();
        list.ReverseIterativaly(list.head);
        list.printList();
    }
}
