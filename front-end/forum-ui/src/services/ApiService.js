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

  /**
   * Fetch current user information
   * @param {number} delay - Milliseconds to delay the response
   * @returns {Promise} - Promise resolving to current user object
   */
  async fetchCurrentUser(delay = 500) {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(db.currentUser);
      }, delay);
    });
  }

  /**
   * Add a new post to the data store
   * @param {Object} post - The post object to add
   * @returns {Promise} - Promise resolving to the new post with ID
   */
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

  /**
   * Like a post by ID
   * @param {number} postId - The ID of the post to like
   * @returns {Promise} - Promise resolving to the updated post
   */
  async likePost(postId) {
    return new Promise((resolve) => {
      // In a real implementation, this would update the server
      resolve({ success: true, postId });
    });
  }

  /**
   * Add a comment to a post
   * @param {number} postId - The ID of the post to comment on
   * @param {Object} comment - The comment object to add
   * @returns {Promise} - Promise resolving to the new comment with ID
   */
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