package com.concordia.message_board.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLFile {

    public Node createPostElement(Document doc, String name, String date, String title, String content,
                                          String attachment) {
        Element post = doc.createElement("Post");

        post.appendChild(createPostElements(doc, post, "name", name));
        post.appendChild(createPostElements(doc, post, "date", date));
        post.appendChild(createPostElements(doc, post, "title", title));
        post.appendChild(createPostElements(doc, post, "content", content));
        post.appendChild(createPostElements(doc, post, "attachment", attachment));

        return post;
    }

    public Node createPostElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
