<template>
    <div>
        <div class="banner">
            <div class="header">
                <div class="logo-container">
                    <img :src="getImageUrl('sjp-logo.jpg')" alt="University Logo" class="logo" />
                </div>
                <div class="title">
                    <h1>Student Forum</h1>
                    <p>Home Feed</p>
                </div>
            </div>
            <div class="navBar">
                <nav>
                    <p><button @click="signOut">Logout</button></p>
                </nav>
            </div>
        </div>
        
        <div class="feed-container">
            <!-- Create post section -->
            <div class="create-post">
                <textarea v-model="postTitle" rows="1" placeholder="Title (optional)"></textarea>
                <textarea v-model="newPostContent" placeholder="Ask a question or share something with the community..."
                    rows="3"></textarea>
                <div class="post-actions">
                    <select v-model="newPostCategory">
                        <option value="" disabled selected>Select category</option>
                        <option v-for="category in categories" :key="category" :value="category">
                            {{ category }}
                        </option>
                    </select>
                    <button @click="createPost">Post</button>
                </div>
            </div>

            <!-- Filter section -->
            <div class="filter-section">
                <div class="filter-options">
                    <button @click="currentFilter = 'all'" :class="{ active: currentFilter === 'all' }">
                        All Posts
                    </button>
                    <button @click="currentFilter = 'questions'" :class="{ active: currentFilter === 'questions' }">
                        Questions
                    </button>
                    <button @click="currentFilter = 'discussions'" :class="{ active: currentFilter === 'discussions' }">
                        Discussions
                    </button>
                    <button @click="currentFilter = 'events'" :class="{ active: currentFilter === 'events' }">
                        Events
                    </button>
                    <button @click="currentFilter = 'announcements'"
                        :class="{ active: currentFilter === 'announcements' }">
                        Announcements
                    </button>
                </div>
                <div class="sort-options">
                    <select v-model="sortBy">
                        <option value="recent">Most Recent</option>
                        <option value="popular">Most Popular</option>
                        <option value="comments">Most Comments</option>
                    </select>
                </div>
            </div>

            <!-- Posts list -->
            <div class="posts-list">
                <div v-if="loading" class="loading">Loading posts...</div>
                <div v-else-if="filteredPosts.length === 0" class="no-posts">
                    No posts available. Be the first to post!
                </div>

                <div v-for="post in filteredPosts" :key="post.id" class="post-card">
                    <div class="post-header">
                        <div class="user-info">
                            <div>
                                <div class="username">{{ post.username }}</div>
                                <div class="post-meta">
                                    <span class="faculty">{{ post.faculty }}</span>
                                    <span class="timestamp">{{
                                        formatTimestamp(post.timestamp)
                                    }}</span>
                                </div>
                            </div>
                        </div>
                        <div class="post-category">{{ post.category }}</div>
                    </div>

                    <div class="post-content">
                        <h3 v-if="post.title" class="post-title">{{ post.title }}</h3>
                        <p>{{ post.content }}</p>
                        <div v-if="post.image" class="post-image">
                            <img :src="post.image" alt="Post Image" />
                        </div>
                    </div>

                    <div class="post-actions">
                        <div class="action-buttons">
                            <button @click="likePost(post.id)" class="like-button">
                                <span>👍</span> {{ post.likes }}
                            </button>
                            <button @click="showComments(post.id)" class="comment-button">
                                <span>💬</span> {{ post.comments.length }}
                            </button>
                            <button @click="sharePost(post.id)" class="share-button">
                                <span>↗️</span> Share
                            </button>
                        </div>
                    </div>

                    <!-- Comments section (toggled by clicking comment button) -->
                    <div v-if="post.showComments" class="comments-section">
                        <div v-for="comment in post.comments" :key="comment.id" class="comment">
                            <div class="comment-header">
                                <div>
                                    <div class="comment-username">{{ comment.username }}</div>
                                    <div class="comment-timestamp">
                                        {{ formatTimestamp(comment.timestamp) }}
                                    </div>
                                </div>
                            </div>
                            <div class="comment-content">{{ comment.content }}</div>
                        </div>

                        <div class="add-comment">
                            <input v-model="newComments[post.id]" placeholder="Add a comment..."
                                @keyup.enter="addComment(post.id)" />
                            <button @click="addComment(post.id)">Send</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import apiService from '@/services/ApiService';
import { signOut } from 'firebase/auth';

export default {
    name: "HomeFeed",
    data() {
        return {
            loading: true,
            posts: [],
            postTitle: "",
            newPostContent: "",
            newPostCategory: "",
            currentFilter: "all",
            sortBy: "recent",
            categories: [
                "Question",
                "Discussion",
                "Event",
                "Announcement",
            ],
            newComments: {},
            currentUser: null,
        };
    },
    computed: {
        filteredPosts() {
            let result = [...this.posts];

            // Define filterMap to map categories
            const filterMap = {
                questions: "Question",
                discussions: "Discussion",
                events: "Event",
                announcements: "Announcement",
            }

            // Apply filterMap
            if (this.currentFilter !== "all" && filterMap[this.currentFilter]) {
                result = result.filter(post => post.category === filterMap[this.currentFilter]);
            }

            // Define sortingMap to map sorting options
            const sortingMap = {
                recent: (a, b) => b.timestamp - a.timestamp,
                popular: (a, b) => b.likes - a.likes,
                comments: (a, b) => b.comments.length - a.comments.length,
            }

            // Apply sortingMap
            if (this.sortBy in sortingMap) {
                result.sort(sortingMap[this.sortBy]);
            }

            return result;
        },
    },
    methods: {
        getImageUrl(imageName) {
            return require(`@/assets/${imageName}`);
        },
        async fetchPosts() {
            this.loading = true;
            try {
                // Call API service to fetch posts
                this.posts = await apiService.fetchPosts();
            } catch (error) {
                console.error('Error fetching posts:', error);
                // Handle error state
            } finally {
                this.loading = false;
            }
        },
        async fetchCurrentUser() {
            try {
                // Call API service to fetch current user
                this.currentUser = await apiService.fetchCurrentUser();
            } catch (error) {
                console.error('Error fetching current user:', error);
                // Set a default user in case of error
                this.currentUser = {
                    id: 1,
                    username: "DefaultUser",
                    faculty: "Unknown",
                };
            }
        },
        formatTimestamp(timestamp) {
            const now = Date.now();
            const diff = now - timestamp;

            if (diff < 60000) {
                return "just now";
            } else if (diff < 3600000) {
                return `${Math.floor(diff / 60000)} min ago`;
            } else if (diff < 86400000) {
                return `${Math.floor(diff / 3600000)} hours ago`;
            } else {
                const date = new Date(timestamp);
                return date.toLocaleDateString();
            }
        },
        async createPost() {
            if (!this.newPostContent.trim()) {
                alert("Please enter content");
                return;
            } else if (!this.newPostCategory) {
                alert("Please select a category");
                return;
            }

            // Ensure currentUser is available
            if (!this.currentUser) {
                await this.fetchCurrentUser();
            }

            const postData = {
                username: this.currentUser.username,
                faculty: this.currentUser.faculty,
                title: this.postTitle.trim() || null, // Use null if empty
                content: this.newPostContent,
                category: this.newPostCategory,
            };

            try {
                // Call API service to create post
                const newPost = await apiService.createPost(postData);

                // Add to beginning of array to show at top
                this.posts.unshift(newPost);

                // Reset form
                this.postTitle = "";
                this.newPostContent = "";
                this.newPostCategory = "";
            } catch (error) {
                console.error('Error creating post:', error);
                alert('Failed to create post. Please try again.');
            }
        },
        async likePost(postId) {
            try {
                // Call API service to like post
                await apiService.likePost(postId);

                // Update local state
                const post = this.posts.find((p) => p.id === postId);
                if (post) {
                    post.likes++;
                }
            } catch (error) {
                console.error('Error liking post:', error);
                // Handle error (possibly revert the like)
            }
        },
        showComments(postId) {
            const post = this.posts.find((p) => p.id === postId);
            if (post) {
                post.showComments = !post.showComments;
            }
        },
        async addComment(postId) {
            const post = this.posts.find((p) => p.id === postId);
            if (!post) return;

            const commentContent = this.newComments[postId];
            if (!commentContent || !commentContent.trim()) return;

            // Ensure currentUser is available
            if (!this.currentUser) {
                await this.fetchCurrentUser();
            }

            const commentData = {
                username: this.currentUser.username,
                content: commentContent,
            };

            try {
                // Call API service to add comment
                const response = await apiService.addComment(postId, commentData);

                if (response.success && response.comment) {
                    // Add comment to local state
                    post.comments.push(response.comment);
                    this.newComments[postId] = "";
                }
            } catch (error) {
                console.error('Error adding comment:', error);
                alert('Failed to add comment. Please try again.');
            }
        },
        sharePost(postId) {
            // This would implement sharing functionality
            alert(`Sharing post ${postId} - implement your sharing logic here`);
        },
    },
    async created() {
        // Fetch data when component is created
        await this.fetchCurrentUser();
        await this.fetchPosts();
    },
};
</script>

<style scoped>
/* Banner styling */
.banner {
    display: flex;
    max-width: 800px;
    margin: 10px auto;
    justify-content: space-between;
    align-items: flex-center;
}

.header {
    display: flex;
    align-items: center;
    height: 80px;
}

.title {
    display: flex;
    flex-direction: column;
    padding-left: 5px;
    justify-content: center;
    align-items: flex-start;
    border-left-width: 2px;
    border-left-color: #ddd;
    border-left-style: solid;
}

.title h1 {
    margin: 0;
    color: #555;
}

.title p {
    font-size: small;
    text-transform: uppercase;
    color: #aaa;
    margin: 5px 0 0 0;
}

.logo-container {
    padding: 0 5px 5px 0;
    max-width: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.logo {
    width: 100%;
    object-fit: cover;
}

/* Feed container */
.feed-container {
    max-width: 800px;
    margin: 20px auto;
    background: white;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

/* Create post section */
.create-post {
    padding: 20px;
    border-bottom: 1px solid #eee;
}

.create-post textarea {
    width: 100%;
    padding: 15px 0 15px 3px;
    border: 1px solid #ddd;
    border-radius: 8px;
    resize: none;
    font-size: 16px;
    color: #555;
    margin-bottom: 10px;
}

.post-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.post-actions select {
    padding: 8px 15px;
    border: 1px solid #ddd;
    border-radius: 20px;
    color: #555;
    background-color: white;
    width: 45%;
}

button {
    background: #c3262d;
    border: 0;
    padding: 10px 20px;
    color: white;
    border-radius: 20px;
    cursor: pointer;
    font-weight: bold;
}

/* Filter section */
.filter-section {
    padding: 15px 20px;
    border-bottom: 1px solid #eee;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.filter-options button {
    background: transparent;
    color: #555;
    border: 0;
    padding: 8px 15px;
    margin-right: 5px;
    cursor: pointer;
    border-radius: 20px;
}

.filter-options button.active {
    background: #f0f0f0;
    font-weight: bold;
}

.sort-options select {
    padding: 8px 15px;
    border: 1px solid #ddd;
    border-radius: 20px;
    color: #555;
    background-color: white;
}

/* Posts list */
.posts-list {
    padding: 20px;
}

.loading,
.no-posts {
    text-align: center;
    padding: 20px;
    color: #888;
}

.post-card {
    border: 1px solid #eee;
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 20px;
    background-color: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.post-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 15px;
}

.user-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    text-align: left;
}

.username {
    font-weight: bold;
    color: #333;
}

.post-meta {
    display: flex;
    font-size: 12px;
    color: #888;
}

.faculty {
    margin-right: 10px;
}

.post-category {
    font-size: 12px;
    color: white;
    background: #c3262d;
    padding: 3px 10px;
    border-radius: 10px;
}

.post-content {
    margin-bottom: 15px;
}

.post-title {
    margin-top: 0;
    margin-bottom: 10px;
    color: #333;
}

.post-image {
    margin-top: 15px;
    border-radius: 8px;
    overflow: hidden;
}

.post-image img {
    width: 100%;
    max-height: 300px;
    object-fit: cover;
}

/* Action buttons */
.action-buttons {
    display: flex;
    gap: 10px;
}

.like-button,
.comment-button,
.share-button {
    background: transparent;
    color: #555;
    border: 1px solid #ddd;
    padding: 5px 10px;
    border-radius: 20px;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 5px;
}

/* Comments section */
.comments-section {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

.comment {
    padding: 10px;
    border-bottom: 1px solid #f5f5f5;
}

.comment-header {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-bottom: 5px;
}

.comment-username {
    font-weight: bold;
    font-size: 14px;
    color: #333;
}

.comment-timestamp {
    font-size: 12px;
    color: #888;
}

.comment-content {
    margin-top: 5px;
    color: #333;
}

.add-comment {
    display: flex;
    margin-top: 15px;
    gap: 10px;
}

.add-comment input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 20px;
    color: #555;
}

.add-comment button {
    background: #c3262d;
    border: 0;
    padding: 5px 15px;
    color: white;
    border-radius: 20px;
    cursor: pointer;
}

.navBar {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    
}
</style>