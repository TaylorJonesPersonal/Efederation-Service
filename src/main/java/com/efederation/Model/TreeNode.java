package com.efederation.Model;

import com.efederation.Enums.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String text;

    //children will inherit the type from their parent
    @Enumerated(EnumType.STRING)
    private NodeType type;

    private String[] availableOptions;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private TreeNode parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<TreeNode> children;

    private TreeNode() {
        super();
    }

    // node has one child and a parent - no available options - passes through to next child
    public TreeNode(String name, String text, @NotNull List<TreeNode> children, TreeNode parent) {
        for(TreeNode child : children) {
            child.setType(this.type);
        }
        if(children.size() > 1) {
            this.children = List.of(children.get(0));
        } else {
            this.children = children;
        }
        this.name = name;
        this.text = text;
        this.parent = parent;
    }

    // node has children and a parent - has available options
    public TreeNode(String name, String text, @NotNull List<TreeNode> children, TreeNode parent, String[] availableOptions) {
        for(TreeNode child : children) {
            child.setType(this.type);
        }
        this.name = name;
        this.text = text;
        this.children = children;
        this.parent = parent;
        this.availableOptions = availableOptions;
    }

    // node is root and has child - no available options - passes through to next child
    public TreeNode(String name, String text, NodeType type, @NotNull List<TreeNode> children) {
        for(TreeNode child : children) {
            child.setType(this.type);
        }
        if(children.size() > 1) {
            this.children = List.of(children.get(0));
        } else {
            this.children = children;
        }
        this.name = name;
        this.text = text;
        this.type = type;
    }

    // node is root and has children - has available options
    public TreeNode(String name, String text, NodeType type, @NotNull List<TreeNode> children, String[] availableOptions) {
        for(TreeNode child : children) {
            child.setType(this.type);
        }

        this.name = name;
        this.text = text;
        this.type = type;
        this.children = children;
        this.availableOptions = availableOptions;
    }

    // node is root and has no children
    public TreeNode(String name, String text, NodeType type) {
        this.name = name;
        this.text = text;
        this.type = type;
    }
}
