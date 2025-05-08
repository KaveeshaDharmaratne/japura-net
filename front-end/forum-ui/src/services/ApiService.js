import db from '@/assets/db.json';

class ApiService {

  async fetchPosts(delay = 1000) {
    return new Promise((resolve) => {
      setTimeout(() => {
        const posts = db.posts.map(post => ({ ...post, showComments: post.showComments || false}));
        resolve(posts);
      }, delay);
    });
  }

  async fetchCurrentUser(delay = 500) {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(db.currentUser);
      }, delay);
    });
  }

  async fetchUserDetails(userId) {
    // Replace with your API endpoint or database query
    const response = await fetch(`/api/users/${userId}`);
    if (!response.ok) {
        throw new Error("Failed to fetch user details");
    }
    return await response.json();
}

  async createPost(post) {
    return new Promise((resolve) => {
      // Get the maximum ID and increment by 1
      const maxId = Math.max(...db.posts.map(p => p.id), 0);
      const newPost = {
        ...post,
        id: maxId + 1,
        timestamp: Date.now(),
        likes: 0,
        showComments: false,
        comments: []
      };
      
      // In a real implementation, this would update the server
      // For this demo, we would return the new post without modifying the JSON file
      resolve(newPost);
    });
  }

  async likePost(postId) {
    return new Promise((resolve) => {
      // In a real implementation, this would update the server
      resolve({ success: true, postId });
    });
  }

  async addComment(postId, comment) {
    return new Promise((resolve) => {
      // Find the post to comment on
      const post = db.posts.find(p => p.id === postId);
      
      if (!post) {
        resolve({ success: false, message: "Post not found" });
        return;
      }
      
      // Get the maximum comment ID and increment by 1
      const maxCommentId = Math.max(...post.comments.map(c => c.id), 0);
      const newComment = {
        ...comment,
        id: maxCommentId + 1,
        timestamp: Date.now()
      };
      
      // In a real implementation, this would update the server
      resolve({ success: true, comment: newComment });
    });
  }
}

// Create a singleton instance
const apiService = new ApiService();

export default apiService;