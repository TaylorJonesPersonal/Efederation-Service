package com.efederation.Service;

import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateNodeRequest;
import com.efederation.Exception.ChildNodeNotFoundException;
import com.efederation.Exception.NodeRequiresParentException;
import com.efederation.Exception.ParentNodeNotFoundException;
import com.efederation.Model.TreeNode;

public interface TreeNodeService {
    ApiResponse<TreeNode> addNode(CreateNodeRequest request) throws ChildNodeNotFoundException, ParentNodeNotFoundException, NodeRequiresParentException;
}
