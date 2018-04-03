package com.tbug.ball.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.TreeDto;

public class BuildTree {

	public static <T> TreeDto<T> build(List<TreeDto<T>> nodes) {

		if (nodes == null) {
			return null;
		}
		List<TreeDto<T>> topNodes = new ArrayList<TreeDto<T>>();

		for (TreeDto<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || "0".equals(pid)) {
				topNodes.add(children);

				continue;
			}

			for (TreeDto<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);
					continue;
				}
			}

		}

		TreeDto<T> root = new TreeDto<T>();
		if (topNodes.size() == 1) {
			root = topNodes.get(0);
		} else {
			root.setId("-1");
			root.setParentId("");
			root.setHasParent(false);
			root.setChildren(true);
			root.setChecked(true);
			root.setChildren(topNodes);
			root.setText("顶级节点");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			root.setState(state);
		}

		return root;
	}

	public static <T> List<TreeDto<T>> buildList(List<TreeDto<T>> nodes, String idParam) {
		if (nodes == null) {
			return null;
		}
		List<TreeDto<T>> topNodes = new ArrayList<TreeDto<T>>();

		for (TreeDto<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || idParam.equals(pid)) {
				topNodes.add(children);

				continue;
			}

			for (TreeDto<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);

					continue;
				}
			}

		}
		return topNodes;
	}

}